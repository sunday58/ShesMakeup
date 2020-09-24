package com.sundaydavid989.shesmakeup.ui.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sundaydavid989.shesmakeup.R
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.data.db.entity.ProductItem
import com.sundaydavid989.shesmakeup.databinding.MakeUpListItemBinding
import com.sundaydavid989.shesmakeup.internal.glide.GlideApp

class ProductTypeDetailAdapter(private val makeupList: List<ProductItem>, private val context: Context)
    :RecyclerView.Adapter<ProductTypeDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MakeUpListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = makeupList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(makeupList[position]) {
                binding.makeupName.text = name
                binding.brandName.text = brand
                GlideApp.with(context)
                    .load(image_link)
                    .into(binding.makeUpImage)

                //Navigation
                holder.itemView.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putSerializable("detailMakeups", makeupList[position])
                    Navigation.findNavController(itemView).navigate(R.id.itemDetailFragment, bundle)
                }
            }
        }
    }

    class ViewHolder( val binding: MakeUpListItemBinding)
        : RecyclerView.ViewHolder(binding.root)
}