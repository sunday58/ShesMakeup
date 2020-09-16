package com.sundaydavid989.shesmakeup.ui.productType

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.sundaydavid989.shesmakeup.R
import com.sundaydavid989.shesmakeup.data.db.entity.ProductTypeItem
import com.sundaydavid989.shesmakeup.databinding.ProductTypeFragmentBinding
import com.sundaydavid989.shesmakeup.ui.adapters.ProductTypeAdapter

class ProductTypeFragment : Fragment() {

    private var _binding: ProductTypeFragmentBinding? = null
    private val binding get() = _binding
    private lateinit var productType: ArrayList<ProductTypeItem>

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
        productType.add(ProductTypeItem(R.drawable.ic_blush, "Blush"))
        productType.add(ProductTypeItem(R.drawable.ic_bronzer, "Bronzer"))
        productType.add(ProductTypeItem(R.drawable.ic_eyebrow, "Eyebrow"))
        productType.add(ProductTypeItem(R.drawable.ic_eyeliner, "Eyeliner"))
        productType.add(ProductTypeItem(R.drawable.ic_eyeshadow, "Eyeshadow"))
        productType.add(ProductTypeItem(R.drawable.ic_foundation, "Foundation"))
        productType.add(ProductTypeItem(R.drawable.ic_lip_liner, "LipLiner"))
        productType.add(ProductTypeItem(R.drawable.ic_lipstick, "Lipstick"))
        productType.add(ProductTypeItem(R.drawable.ic_mascara, "Mascara"))
        productType.add(ProductTypeItem(R.drawable.ic_nail_polish, "NailPolish"))

        setData()
    }

    private fun setData(){
        binding!!.productTypeRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding!!.productTypeRecyclerView.adapter = ProductTypeAdapter(productType, requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}