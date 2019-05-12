package com.linhtt.photoeditor.adapter

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.core.BaseRecycleViewAdapter
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.data.model.Font

class FontAdapter(val items: ArrayList<Font>) : BaseRecycleViewAdapter<Font>() {
    override fun getItem(position: Int): Font {
        return items[position]
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BaseViewHolder {
        val binding: com.linhtt.photoeditor.databinding.ItemFontBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.item_font, p0, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(private val binding: com.linhtt.photoeditor.databinding.ItemFontBinding) :
        BaseViewHolder(binding.root) {
        override fun onBindingData(position: Int) {
            binding.font = getItem(position)
            binding.executePendingBindings()
        }

    }
}