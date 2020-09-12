package com.sundaydavid989.shesmakeup.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.databinding.FragmentHomeDetailBinding

class HomeDetailFragment : Fragment() {

    private var _binding: FragmentHomeDetailBinding? = null
    private val binding get() = _binding
    lateinit var makeups: MakeupItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentHomeDetailBinding.inflate(inflater, container, false)

        detailMakeup()
        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    private fun detailMakeup(){
        if (arguments != null && requireArguments().containsKey("makeups")){
            makeups = requireArguments().getSerializable("makeups") as MakeupItem

            binding!!.detailPrice.text = makeups.priceSign + makeups.price
            binding!!.detailProductName.text = makeups.name
            binding!!.detailCategory.text = makeups.category
        }
    }

}