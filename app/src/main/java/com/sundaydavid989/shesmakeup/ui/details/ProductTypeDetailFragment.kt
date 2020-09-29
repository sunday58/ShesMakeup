package com.sundaydavid989.shesmakeup.ui.details

import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.sundaydavid989.shesmakeup.Constants
import com.sundaydavid989.shesmakeup.data.db.entity.ProductTypeItem
import com.sundaydavid989.shesmakeup.databinding.ProductTypeDetailFragmentBinding
import com.sundaydavid989.shesmakeup.ui.adapters.HomeAdapter
import com.sundaydavid989.shesmakeup.ui.adapters.MakeupLoadStateAdapter
import com.sundaydavid989.shesmakeup.ui.base.ScopedFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ProductTypeDetailFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: ProductTypeDetailFactory by instance()
    private lateinit var viewModel: ProductTypeDetailViewModel
    private var _binding: ProductTypeDetailFragmentBinding? = null
    private val binding get() = _binding
    private lateinit var productName: ProductTypeItem
    private val adapter = HomeAdapter()

    //recycler view state
    private lateinit var bundleRecyclerState: Bundle
    private var listState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundleRecyclerState = Bundle()
    }

    private fun makeups() = launch {
        if (arguments != null && requireArguments().containsKey("product")){
            productName = requireArguments().getSerializable("product") as ProductTypeItem

            binding!!.productTypeDetailRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            viewModel.searchItemType(productName.productTypeName).collect {
                adapter.submitData(it)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProductTypeDetailFragmentBinding.inflate(inflater, container, false)

        initAdapter()
        makeups()
        initPosition()
        binding!!.retryButton.setOnClickListener { adapter.retry() }
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ProductTypeDetailViewModel::class.java)

    }

    private fun initAdapter() {
        binding!!.productTypeDetailRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MakeupLoadStateAdapter { adapter.retry() },
            footer = MakeupLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            //only show the list if refresh succeeds
            binding!!.productTypeDetailRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            //show load spinner during initial load
            binding!!.spinKitProduct.isVisible = loadState.source.refresh is LoadState.Loading
            //show retry state if initial load or refresh fails
            binding!!.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.append as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.append as? LoadState.Error
            errorState?.let {
                showToast("Network error")
            }
        }
    }

    private fun initPosition(){
        lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding!!.productTypeDetailRecyclerView.scrollToPosition(0) }
        }
    }

    @Suppress("SameParameterValue")
    private fun showToast(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        listState = binding!!.productTypeDetailRecyclerView.layoutManager!!.onSaveInstanceState()
        bundleRecyclerState.putParcelable(Constants.KEY_RECYCLER_STATE, listState)
    }

    override fun onResume() {
        super.onResume()
        if (bundleRecyclerState != null) {
            Handler().postDelayed({
                listState = bundleRecyclerState.getParcelable(Constants.KEY_RECYCLER_STATE)
                binding!!.productTypeDetailRecyclerView.layoutManager!!.onRestoreInstanceState(listState)
            }, 50)
        }
        binding!!.productTypeDetailRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}