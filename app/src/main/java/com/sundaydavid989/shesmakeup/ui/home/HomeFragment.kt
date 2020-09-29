package com.sundaydavid989.shesmakeup.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import com.sundaydavid989.shesmakeup.Constants.KEY_RECYCLER_STATE
import com.sundaydavid989.shesmakeup.R
import com.sundaydavid989.shesmakeup.databinding.HomeFragmentBinding
import com.sundaydavid989.shesmakeup.ui.adapters.HomeAdapter
import com.sundaydavid989.shesmakeup.ui.adapters.MakeupLoadStateAdapter
import com.sundaydavid989.shesmakeup.ui.base.ScopedFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

@ExperimentalCoroutinesApi
@FlowPreview
class HomeFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding
    private val adapter = HomeAdapter()

    //recycler view state
    private lateinit var bundleRecyclerState: Bundle
    private var listState: Parcelable? = null

    private fun makeUps() = launch {
        binding!!.homeRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.getMakeup().collect {
            adapter.submitData(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundleRecyclerState = Bundle()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)

        initSpeedDial(savedInstanceState == null)

        initAdapter()
        makeUps()
        initPosition()

        binding!!.retryButton.setOnClickListener { adapter.retry() }
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

    }

    private fun initAdapter() {
        binding!!.homeRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MakeupLoadStateAdapter { adapter.retry() },
            footer = MakeupLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            //only show the list if refresh succeeds
            binding!!.homeRecyclerView.adapter!!.notifyDataSetChanged()
                binding!!.homeRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                //show load spinner during initial load
                binding!!.spinKit.isVisible = loadState.source.refresh is LoadState.Loading
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
                .collect { binding!!.homeRecyclerView.scrollToPosition(0) }
        }
    }


    private fun initSpeedDial(addActionItem:  Boolean){
        if (addActionItem){
            binding!!.speedDial.addActionItem(SpeedDialActionItem.Builder(R.id.brand,
            R.drawable.ic_band)
                .setFabImageTintColor(ResourcesCompat.getColor(resources, R.color.white,
                    requireContext().theme))
                .setLabel("Brand")
                .setLabelColor(ResourcesCompat.getColor(resources, R.color.colorPrimary,
                    requireContext().theme))
                .create())

            binding!!.speedDial.addActionItem(SpeedDialActionItem.Builder(R.id.product_type,
                R.drawable.ic_product_type)
                .setFabImageTintColor(ResourcesCompat.getColor(resources, R.color.white,
                    requireContext().theme))
                .setLabel("Product type")
                .setLabelColor(ResourcesCompat.getColor(resources, R.color.colorPrimary,
                    requireContext().theme))
                .create())

            binding!!.speedDial.addActionItem(SpeedDialActionItem.Builder(R.id.search,
                R.drawable.ic_search)
                .setFabImageTintColor(ResourcesCompat.getColor(resources, R.color.white,
                    requireContext().theme))
                .setLabel("Search")
                .setLabelColor(ResourcesCompat.getColor(resources, R.color.colorPrimary,
                    requireContext().theme))
                .create())

            binding!!.speedDial.setOnChangeListener(object : SpeedDialView.OnChangeListener{
                override fun onMainActionSelected(): Boolean {
                    return false
                }

                override fun onToggleChanged(isOpen: Boolean) {
                    Log.d("Toggle", " Fab Toggled")
                }
            })

            binding!!.speedDial.setOnActionSelectedListener {actionItem ->
                when (actionItem.id) {
                    R.id.brand -> {
                        Navigation.findNavController(binding!!.homeLayout)
                            .navigate(R.id.brandFragment)
                        return@setOnActionSelectedListener true
                    }
                    R.id.product_type -> {
                        Navigation.findNavController(binding!!.homeLayout)
                            .navigate(R.id.productTypeFragment)
                        return@setOnActionSelectedListener true
                    }
                    R.id.search -> {
                        Navigation.findNavController(binding!!.homeLayout)
                            .navigate(R.id.searchFragment)
                        return@setOnActionSelectedListener true
                    }
                  else  -> {
                      false
                  }
                }
            }
        }
    }

    @Suppress("SameParameterValue")
    private fun showToast(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        listState = binding!!.homeRecyclerView.layoutManager!!.onSaveInstanceState()
        bundleRecyclerState.putParcelable(KEY_RECYCLER_STATE, listState)
    }

    override fun onResume() {
        super.onResume()
        if (bundleRecyclerState != null) {
            Handler().postDelayed({
                listState = bundleRecyclerState.getParcelable(KEY_RECYCLER_STATE)
                binding!!.homeRecyclerView.layoutManager!!.onRestoreInstanceState(listState)
            }, 50)
        }
        binding!!.homeRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

}