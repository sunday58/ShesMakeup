package com.sundaydavid989.shesmakeup.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sundaydavid989.shesmakeup.data.db.entity.ProductTypeItem
import com.sundaydavid989.shesmakeup.databinding.ProductTypeDetailFragmentBinding
import com.sundaydavid989.shesmakeup.ui.adapters.ProductTypeDetailAdapter
import com.sundaydavid989.shesmakeup.ui.base.ScopedFragment
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
    private lateinit var adapter: ProductTypeDetailAdapter
    private lateinit var productName: ProductTypeItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProductTypeDetailFragmentBinding.inflate(inflater, container, false)
        binding!!.spinKitProduct.visibility = View.VISIBLE
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ProductTypeDetailViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        if (arguments != null && requireArguments().containsKey("product")){
            productName = requireArguments().getSerializable("product") as ProductTypeItem

            viewModel.fetchProductType(productName.productTypeName)

            binding!!.productTypeDetailRecyclerView.layoutManager =
                GridLayoutManager(requireContext(), 2)
            val products = viewModel.productType.await()
            products.observe(viewLifecycleOwner, Observer {productList ->
                Log.d("Products ", productList.toString())
                if (productList == null) return@Observer
                adapter = ProductTypeDetailAdapter(productList, requireContext())
                binding!!.productTypeDetailRecyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
                binding!!.spinKitProduct.visibility = View.GONE
            })
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}