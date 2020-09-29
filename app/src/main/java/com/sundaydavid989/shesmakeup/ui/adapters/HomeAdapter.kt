package com.sundaydavid989.shesmakeup.ui.adapters

import android.os.Bundle
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.sundaydavid989.shesmakeup.R
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem

class HomeAdapter()
    :PagingDataAdapter<MakeupItem, HomeViewHolder>(REPO_COMPARATOR) {

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val makeupItem = getItem(position)
        if (makeupItem != null){
            holder.bind(makeupItem)

            holder.itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("makeups", makeupItem)
                Navigation.findNavController(holder.itemView).navigate(R.id.itemDetailFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder.create(parent)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<MakeupItem>() {
            override fun areItemsTheSame(oldItem: MakeupItem, newItem: MakeupItem): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: MakeupItem, newItem: MakeupItem): Boolean =
                oldItem == newItem
        }
    }

}