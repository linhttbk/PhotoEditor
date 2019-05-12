package com.linhtt.photoeditor.adapter

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.core.BaseRecycleViewAdapter
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.data.model.Blend


class BlendAdapter(var items: ArrayList<Blend>, var path: String, var bendPath: String) :
    BaseRecycleViewAdapter<Blend>() {
    val TYPE_ADD = 0
    val TYPE_ITEM = 1
    var updateImage = true

    override fun getItem(position: Int): Blend {
        return items[position]
    }

    fun replace(items: ArrayList<Blend>) {
        this.items.clear()
        this.items = items
        notifyDataSetChanged()
    }

    fun replace(blendPath: String) {
        this.bendPath = blendPath
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BaseViewHolder {
        if (p1 == TYPE_ITEM) {
            val binding: com.linhtt.photoeditor.databinding.ItemBlendBinding = DataBindingUtil.inflate(
                LayoutInflater.from(p0.context),
                R.layout.item_blend, p0, false
            )

            return ViewHolder(binding)
        } else {
            val view = LayoutInflater.from(p0.context).inflate(R.layout.item_select_blend, p0, false)
            return SelectImageViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return TYPE_ADD
        return TYPE_ITEM
    }


    inner class ViewHolder(private val binding: com.linhtt.photoeditor.databinding.ItemBlendBinding) :
        BaseViewHolder(binding.root) {
        override fun onBindingData(position: Int) {
            binding.viewModel = getItem(position)
            binding.updateImage = updateImage
            binding.path = path
            binding.blendPath = bendPath
            binding.executePendingBindings()
        }

    }

    inner class SelectImageViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun onBindingData(position: Int) {

        }
    }


    fun updateSelected(position: Int) {

        if (position < 0 || position >= items.size) return
        items.withIndex().first {
            it.value.selected
        }.apply {
            if (position == index) return
            this.value.selected = false
            notifyItemChanged(this.index)
        }
        items[position].selected = true
        notifyItemChanged(position)


    }

    fun clear() {
        if (!items.isEmpty()) {
            for (item in items) {
                item.clear()
            }
        }
        items.clear()
    }
}