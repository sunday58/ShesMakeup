package com.sundaydavid989.shesmakeup.ui.details

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.like.LikeButton
import com.like.OnLikeListener
import com.sundaydavid989.shesmakeup.R
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.data.db.entity.ProductColor
import com.sundaydavid989.shesmakeup.databinding.FragmentItemDetailBinding
import com.sundaydavid989.shesmakeup.internal.glide.GlideApp
import com.sundaydavid989.shesmakeup.ui.adapters.ItemColorAdapter


class ItemDetailFragment : Fragment() {

    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding
    private lateinit var makeups: MakeupItem
    private lateinit var sheetBehavior: BottomSheetBehavior<View>
    private lateinit var adapter: ItemColorAdapter
    private var colors = ArrayList<ProductColor>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentItemDetailBinding.inflate(inflater, container, false)

        sheetBehavior = BottomSheetBehavior.from(binding!!.bottomSheet)
        checkBottomSheet()
        detailMakeup()
        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    private fun detailMakeup(){
        if (arguments != null && requireArguments().containsKey("makeups")){
            makeups = requireArguments().getSerializable("makeups") as MakeupItem

            binding!!.detailPrice.text = "$" + makeups.price
            binding!!.detailProductName.text = makeups.name
            binding!!.detailCategory.text = makeups.category
            binding!!.description.text = makeups.description
            GlideApp.with(requireContext())
                .load(makeups.imageLink)
                .into(binding!!.makeupDetailImage)

            binding!!.makeupDetailImage.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("makeupImage", makeups)
                Navigation.findNavController(requireView()).navigate(R.id.zoomImageFragment, bundle)
            }

            // for colors
            binding!!.itemColorsRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
            colors.addAll(makeups.productColors)
            adapter = ItemColorAdapter(colors, requireContext())
            binding!!.itemColorsRecyclerView.adapter = adapter
            binding!!.itemColorsRecyclerView.adapter!!.notifyDataSetChanged()

            //open sale url
            binding!!.detailBuy.setOnClickListener {
                val buyIntent = Intent(Intent.ACTION_VIEW, Uri.parse(makeups.productLink))
                startActivity(buyIntent)
            }

        }
    }

    private fun checkBottomSheet(){
        binding!!.makeupDescription.setOnClickListener {
            if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED){
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }else {
                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun addToFavorite(){
        binding!!.likeButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                TODO("Not yet implemented")
            }

            override fun unLiked(likeButton: LikeButton?) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}