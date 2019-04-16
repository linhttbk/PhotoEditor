package com.linhtt.photoeditor.adapter

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.core.BaseRecycleViewAdapter
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.data.model.Adjustment
import java.lang.Exception

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
            binding.executePendingBindings()
        }

    }

    fun updateSelected(position: Int) {
        if (position < 0 || position >= items.size) return
        try {
            items.withIndex().first {
                it.value.isSelected
            }.apply { this.value.isSelected = false
                notifyItemChanged(this.index)
            }
        }catch (ex:Exception){
            ex.printStackTrace()
        }

        items[position].isSelected = true

        notifyItemChanged(position)
    }
    fun getSelectedItem():Adjustment?{
        try {
            items.withIndex().first {
                it.value.isSelected
            }.apply {
                return this.value
            }
        }catch (ex:Exception){
            ex.printStackTrace()
            return null
        }
    }
    fun getSelectedPosition():Int{
        try {
            items.withIndex().first {
                it.value.isSelected
            }.apply {
                return index
            }
        }catch (ex:Exception){
            ex.printStackTrace()
            return -1
        }
    }
}