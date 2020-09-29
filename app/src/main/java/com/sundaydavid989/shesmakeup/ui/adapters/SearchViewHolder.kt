package com.sundaydavid989.shesmakeup.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sundaydavid989.shesmakeup.R
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.databinding.MakeUpListItemBinding
import com.sundaydavid989.shesmakeup.databinding.SearchListItemBinding
import com.sundaydavid989.shesmakeup.internal.glide.GlideApp

class SearchViewHolder(
    private val binding: SearchListItemBinding
): RecyclerView.ViewHolder(binding.root) {

    private var makeupList: MakeupItem? = null

    fun bind(makeupList: MakeupItem?){
        if (makeupList == null) {
            val resources = itemView.resources
            binding.brandName.text = resources.getString(R.string.loading)
            binding.searchMakeupName.visibility = View.GONE
            binding.searchMakeUpImage.visibility = View.GONE
        }else {
            showMakeupData(makeupList)
        }
    }

    private fun showMakeupData(makeupList: MakeupItem){
        this.makeupList = makeupList
        binding.searchMakeupName.text = makeupList.name
        binding.brandName.text = makeupList.brand
        GlideApp.with(itemView.context)
            .load(makeupList.imageLink)
            .into(binding.searchMakeUpImage)
    }

    companion object {
        fun create(parent: ViewGroup): SearchViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.search_list_item, parent, false)
            val binding = SearchListItemBinding.bind(view)
            return SearchViewHolder(binding)
        }
    }
}