package com.sundaydavid989.shesmakeup.ui.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sundaydavid989.shesmakeup.R
import com.sundaydavid989.shesmakeup.data.db.entity.ProductColor
import com.sundaydavid989.shesmakeup.data.db.entity.ProductTypeItem
import com.sundaydavid989.shesmakeup.databinding.ColorsListItemBinding
import com.sundaydavid989.shesmakeup.databinding.ProductTypeItemListBinding
import com.sundaydavid989.shesmakeup.internal.glide.GlideApp

class ItemColorAdapter(private val makeupList: List<ProductColor>, private val context: Context)
    :RecyclerView.Adapter<ItemColorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ColorsListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = makeupList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(makeupList[position]) {
                binding.itemColor.setBackgroundColor(Color.parseColor(hexValue))
            }
        }
    }

    class ViewHolder( val binding: ColorsListItemBinding)
        : RecyclerView.ViewHolder(binding.root)
}