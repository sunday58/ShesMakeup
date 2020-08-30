package com.sundaydavid989.shesmakeup.ui.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sundaydavid989.shesmakeup.data.db.entity.Makeup
import com.sundaydavid989.shesmakeup.databinding.MakeUpListItemBinding

class HomeAdapter(private val makeupList: Makeup)
    :RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

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

            }
        }
    }

    inner class ViewHolder( val binding: MakeUpListItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    fun makeupImage(url: String){

    }
}