package com.sundaydavid989.shesmakeup.ui.itemDetails

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
import com.sundaydavid989.shesmakeup.ui.favorite.FavoriteViewModel


class ItemDetailFragment : Fragment() {

    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding
    private lateinit var makeups: MakeupItem
    private lateinit var sheetBehavior: BottomSheetBehavior<View>
    private lateinit var adapter: ItemColorAdapter
    private var colors = ArrayList<ProductColor>()

    private lateinit var viewModel: ItemDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentItemDetailBinding.inflate(inflater, container, false)

        //get bundle
        makeups = requireArguments().getSerializable("makeups") as MakeupItem
        sheetBehavior = BottomSheetBehavior.from(binding!!.bottomSheet)
        checkBottomSheet()
        detailMakeup()
        addToFavorite()
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ItemDetailViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    private fun detailMakeup(){
        if (arguments != null && requireArguments().containsKey("makeups")){

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
                if (!likeButton!!.isLiked){
                    viewModel.addFavorite(makeups)
                }
            }

            override fun unLiked(likeButton: LikeButton?) {
                if (likeButton!!.isLiked){
                    viewModel.deleteFavorite(makeups)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}