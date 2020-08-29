package com.sundaydavid989.shesmakeup.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import com.sundaydavid989.shesmakeup.R
import com.sundaydavid989.shesmakeup.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)

        initSpeedDial(savedInstanceState == null)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

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

    private fun showToast(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}