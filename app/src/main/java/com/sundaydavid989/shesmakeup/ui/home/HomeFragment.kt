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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import com.sundaydavid989.shesmakeup.Constants.KEY_RECYCLER_STATE
import com.sundaydavid989.shesmakeup.R
import com.sundaydavid989.shesmakeup.databinding.HomeFragmentBinding
import com.sundaydavid989.shesmakeup.ui.adapters.HomeAdapter
import com.sundaydavid989.shesmakeup.ui.base.ScopedFragment
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class HomeFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: HomeAdapter

    //recycler view state
    private lateinit var bundleRecyclerState: Bundle
    private var listState: Parcelable? = null

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
        binding!!.spinKit.visibility = View.VISIBLE
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        binding!!.homeRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        val makeups = viewModel.makeup.await()
            makeups.observe(viewLifecycleOwner, Observer { makeupItems ->
                Log.d("Makeups ", makeupItems.toString())
                if (makeupItems == null) return@Observer
                adapter = HomeAdapter(makeupItems, requireContext())
                binding!!.homeRecyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
                binding!!.spinKit.visibility = View.GONE
            })
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}