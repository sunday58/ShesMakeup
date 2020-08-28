package com.sundaydavid989.shesmakeup.ui.brand

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.sundaydavid989.shesmakeup.R
import com.sundaydavid989.shesmakeup.databinding.BrandFragmentBinding

class BrandFragment : Fragment() {

    private lateinit var viewModel: BrandViewModel
    private var _binding: BrandFragmentBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BrandFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BrandViewModel::class.java)

    }

}