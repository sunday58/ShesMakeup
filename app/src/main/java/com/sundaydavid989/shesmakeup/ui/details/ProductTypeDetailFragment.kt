package com.sundaydavid989.shesmakeup.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sundaydavid989.shesmakeup.R

class ProductTypeDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ProductTypeDetailFragment()
    }

    private lateinit var viewModel: ProductTypeDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_type_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductTypeDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}