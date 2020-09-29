package com.sundaydavid989.shesmakeup.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.sundaydavid989.shesmakeup.data.db.entity.ProductTypeItem
import com.sundaydavid989.shesmakeup.databinding.ProductTypeDetailFragmentBinding
import com.sundaydavid989.shesmakeup.ui.base.ScopedFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ProductTypeDetailFragment : ScopedFragment() {

    private lateinit var viewModel: ProductTypeDetailViewModel
    private var _binding: ProductTypeDetailFragmentBinding? = null
    private val binding get() = _binding
    private lateinit var productName: ProductTypeItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProductTypeDetailFragmentBinding.inflate(inflater, container, false)
        binding!!.spinKitProduct.visibility = View.INVISIBLE
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)
            .get(ProductTypeDetailViewModel::class.java)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}