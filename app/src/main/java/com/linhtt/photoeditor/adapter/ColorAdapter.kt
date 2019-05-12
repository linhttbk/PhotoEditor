package com.linhtt.photoeditor.adapter

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.core.BaseRecycleViewAdapter
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.data.model.ColorText
import kotlinx.android.synthetic.main.item_color.view.*

class ColorAdapter(val items: ArrayList<ColorText>) : BaseRecycleViewAdapter<ColorText>() {
    override fun getItem(position: Int): ColorText {
        return items[position]
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BaseViewHolder {
        val binding: com.linhtt.photoeditor.databinding.ItemColorBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.item_color, p0, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(private val binding:com.linhtt.photoeditor.databinding.ItemColorBinding) :
        BaseViewHolder(binding.root) {

        init {
            val params = binding.root.btnColor.layoutParams
            params.height = params.width
            binding.root.btnColor.layoutParams = params
        }
        override fun onBindingData(position: Int) {
            binding.color = getItem(position)
            binding.executePendingBindings()
        }

    }
}