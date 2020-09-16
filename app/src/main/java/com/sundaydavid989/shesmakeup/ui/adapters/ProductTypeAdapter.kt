package com.sundaydavid989.shesmakeup.ui.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sundaydavid989.shesmakeup.R
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.data.db.entity.ProductTypeItem
import com.sundaydavid989.shesmakeup.databinding.MakeUpListItemBinding
import com.sundaydavid989.shesmakeup.databinding.ProductTypeItemListBinding
import com.sundaydavid989.shesmakeup.internal.glide.GlideApp

class ProductTypeAdapter(private val makeupList: List<ProductTypeItem>, private val context: Context)
    :RecyclerView.Adapter<ProductTypeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductTypeItemListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = makeupList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(makeupList[position]) {
                binding.productTypeTitle.text = productTypeName
                GlideApp.with(context)
                    .load(productTypeImage)
                    .into(binding.productTypeImage)

                //Navigation

            }
        }
    }

    class ViewHolder( val binding: ProductTypeItemListBinding)
        : RecyclerView.ViewHolder(binding.root)
}