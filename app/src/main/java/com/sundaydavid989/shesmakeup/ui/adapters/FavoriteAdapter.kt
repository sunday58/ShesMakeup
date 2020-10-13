package com.sundaydavid989.shesmakeup.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sundaydavid989.shesmakeup.R
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.databinding.SearchListItemBinding
import com.sundaydavid989.shesmakeup.internal.glide.GlideApp

class FavoriteAdapter(private val favMakeups: List<MakeupItem>)
    :RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = SearchListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        with(holder){
            with(favMakeups[position]){
                binding.searchMakeupName.text = name
                binding.brandName.text = brand
                GlideApp.with(itemView.context)
                    .load(imageLink)
                    .into(binding.searchMakeUpImage)

                holder.itemView.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putSerializable("makeups", favMakeups[position])
                    Navigation.findNavController(holder.itemView).navigate(R.id.itemDetailFragment, bundle)
                }
            }
        }
    }

    override fun getItemCount() = favMakeups.size

     class FavoriteViewHolder(val binding: SearchListItemBinding)
        :RecyclerView.ViewHolder(binding.root)
}