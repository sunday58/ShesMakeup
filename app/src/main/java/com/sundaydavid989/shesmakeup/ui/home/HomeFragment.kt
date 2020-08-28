package com.sundaydavid989.shesmakeup.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
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
                .create())

            binding!!.speedDial.addActionItem(SpeedDialActionItem.Builder(R.id.product_type,
                R.drawable.ic_product_type)
                .create())

            binding!!.speedDial.addActionItem(SpeedDialActionItem.Builder(R.id.search,
                R.drawable.ic_search)
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
                        showToast("action clicked")
                        return@setOnActionSelectedListener true
                    }
                    R.id.product_type -> {
                        showToast("action 2 clicked")
                        return@setOnActionSelectedListener true
                    }
                    R.id.search -> {
                        showToast("action 3 clicked")
                        return@setOnActionSelectedListener true
                    }
                  else  -> {
                      true
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