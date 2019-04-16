package com.linhtt.photoeditor.adapter

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.core.BaseRecycleViewAdapter
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.data.model.Adjustment

class AdjustmentAdapter (val items: ArrayList<Adjustment>) : BaseRecycleViewAdapter<Adjustment>() {
    override fun getItem(position: Int): Adjustment = items[position]

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BaseViewHolder {
        val binding: com.linhtt.photoeditor.databinding.ItemAdjustBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.item_adjust, p0, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size
    inner class ViewHolder(private val binding: com.linhtt.photoeditor.databinding.ItemAdjustBinding) :
        BaseViewHolder(binding.root) {
        override fun onBindingData(position: Int) {
            binding.adjust = getItem(position)
        }

    }
}