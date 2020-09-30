package com.sundaydavid989.shesmakeup.ui.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sundaydavid989.shesmakeup.data.db.entity.ProductColor
import com.sundaydavid989.shesmakeup.databinding.ColorsListItemBinding

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

                itemView.setOnClickListener {
                    Toast.makeText(itemView.context, colourName, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    class ViewHolder( val binding: ColorsListItemBinding)
        : RecyclerView.ViewHolder(binding.root)
}