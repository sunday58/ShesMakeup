package com.sundaydavid989.shesmakeup.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sundaydavid989.shesmakeup.R
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.databinding.MakeUpListItemBinding
import com.sundaydavid989.shesmakeup.internal.glide.GlideApp

class HomeAdapter(private val makeupList: List<MakeupItem>)
    :PagingDataAdapter<MakeupItem, HomeAdapter.ViewHolder>(REPO_COMPARATOR) {

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
                GlideApp.with(itemView.context)
                    .load(imageLink)
                    .into(binding.makeUpImage)

                //Navigation
                holder.itemView.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putSerializable("makeups", makeupList[position])
                    Navigation.findNavController(itemView).navigate(R.id.itemDetailFragment, bundle)
                }
            }
        }
    }

    class ViewHolder( val binding: MakeUpListItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<MakeupItem>() {
            override fun areItemsTheSame(oldItem: MakeupItem, newItem: MakeupItem): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: MakeupItem, newItem: MakeupItem): Boolean =
                oldItem == newItem
        }
    }
}