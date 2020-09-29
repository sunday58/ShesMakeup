package com.sundaydavid989.shesmakeup.ui.productType

import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.sundaydavid989.shesmakeup.Constants
import com.sundaydavid989.shesmakeup.R
import com.sundaydavid989.shesmakeup.data.db.entity.ProductTypeItem
import com.sundaydavid989.shesmakeup.databinding.ProductTypeFragmentBinding
import com.sundaydavid989.shesmakeup.ui.adapters.ProductTypeAdapter

class ProductTypeFragment : Fragment() {

    private var _binding: ProductTypeFragmentBinding? = null
    private val binding get() = _binding
    private lateinit var productType: ArrayList<ProductTypeItem>

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
        _binding = ProductTypeFragmentBinding.inflate(inflater, container, false)

        addProductItems()
        return binding?.root
    }

    private fun addProductItems(){
        productType = ArrayList()
        productType.add(ProductTypeItem(R.drawable.ic_blush, "blush"))
        productType.add(ProductTypeItem(R.drawable.ic_bronzer, "bronzer"))
        productType.add(ProductTypeItem(R.drawable.ic_eyebrow, "eyebrow"))
        productType.add(ProductTypeItem(R.drawable.ic_eyeliner, "eyeliner"))
        productType.add(ProductTypeItem(R.drawable.ic_eyeshadow, "eyeshadow"))
        productType.add(ProductTypeItem(R.drawable.ic_foundation, "foundation"))
        productType.add(ProductTypeItem(R.drawable.ic_lip_liner, "lip_liner"))
        productType.add(ProductTypeItem(R.drawable.ic_lipstick, "lipstick"))
        productType.add(ProductTypeItem(R.drawable.ic_mascara, "mascara"))
        productType.add(ProductTypeItem(R.drawable.ic_nail_polish, "nail_polish"))

        setData()
    }

    private fun setData(){
        binding!!.productTypeRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding!!.productTypeRecyclerView.adapter = ProductTypeAdapter(productType, requireContext())
    }

    override fun onPause() {
        super.onPause()
        listState = binding!!.productTypeRecyclerView.layoutManager!!.onSaveInstanceState()
        bundleRecyclerState.putParcelable(Constants.KEY_RECYCLER_STATE, listState)
    }

    override fun onResume() {
        super.onResume()
        if (bundleRecyclerState != null) {
            Handler().postDelayed({
                listState = bundleRecyclerState.getParcelable(Constants.KEY_RECYCLER_STATE)
                binding!!.productTypeRecyclerView.layoutManager!!.onRestoreInstanceState(listState)
            }, 50)
        }
        binding!!.productTypeRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

}