package com.linhtt.photoeditor.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.core.BaseRecycleViewAdapter
import com.linhtt.photoeditor.MainApplication
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.data.model.Filter

class FilterAdapter(val items: ArrayList<Filter>) : BaseRecycleViewAdapter<Filter>() {
    override fun getItem(position: Int): Filter = items[position]

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BaseViewHolder {
        val binding: com.linhtt.photoeditor.databinding.ItemEditorBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.item_editor, p0, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size
    inner class ViewHolder(private val binding: com.linhtt.photoeditor.databinding.ItemEditorBinding) :
        BaseViewHolder(binding.root) {
        override fun onBindingData(position: Int) {
            binding.filter = getItem(position)
            binding.executePendingBindings()
        }

    }

}