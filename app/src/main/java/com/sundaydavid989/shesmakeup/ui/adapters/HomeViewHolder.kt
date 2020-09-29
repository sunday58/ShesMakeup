package com.sundaydavid989.shesmakeup.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sundaydavid989.shesmakeup.R
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.databinding.MakeUpListItemBinding
import com.sundaydavid989.shesmakeup.internal.glide.GlideApp

class HomeViewHolder(
    private val binding: MakeUpListItemBinding
): RecyclerView.ViewHolder(binding.root) {

    private var makeupList: MakeupItem? = null

    fun bind(makeupList: MakeupItem?){
        if (makeupList == null) {
            val resources = itemView.resources
            binding.brandName.text = resources.getString(R.string.loading)
            binding.makeupName.visibility = View.GONE
            binding.makeUpImage.visibility = View.GONE
        }else {
            showMakeupData(makeupList)
        }
    }

    private fun showMakeupData(makeupList: MakeupItem){
        this.makeupList = makeupList
        binding.makeupName.text = makeupList.name
        binding.brandName.text = makeupList.brand
        GlideApp.with(itemView.context)
            .load(makeupList.imageLink)
            .into(binding.makeUpImage)
    }

    companion object {
        fun create(parent: ViewGroup): HomeViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.make_up_list_item, parent, false)
            val binding = MakeUpListItemBinding.bind(view)
            return HomeViewHolder(binding)
        }
    }
}