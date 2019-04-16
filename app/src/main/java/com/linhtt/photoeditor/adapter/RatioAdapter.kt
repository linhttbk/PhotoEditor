package com.linhtt.photoeditor.adapter

import android.databinding.DataBindingUtil
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.core.BaseRecycleViewAdapter
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.data.model.Ratio

class RatioAdapter(private val items: ArrayList<Ratio>) : BaseRecycleViewAdapter<Ratio>() {
    override fun getItem(position: Int): Ratio = items[position]

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BaseViewHolder {
        val binding: com.linhtt.photoeditor.databinding.ItemRatioBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.item_ratio, p0, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size
    inner class ViewHolder(private val binding: com.linhtt.photoeditor.databinding.ItemRatioBinding) :
        BaseViewHolder(binding.root) {
        override fun onBindingData(position: Int) {
            binding.ratio = getItem(position)
            binding.executePendingBindings()
        }

    }

    fun updateSelected(position: Int) {
        if (position < 0 || position >= items.size) return
        items.withIndex().first {
            it.value.isSelected
        }.apply { this.value.isSelected = false
                notifyItemChanged(this.index)
        }
        items[position].isSelected = true

       notifyItemChanged(position)
    }
}