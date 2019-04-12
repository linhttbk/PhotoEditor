package com.linhtt.photoeditor.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.core.BaseRecycleViewAdapter
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.data.model.Photo

class FolderAdapter(var items: ArrayList<Photo>) : BaseRecycleViewAdapter<Photo>() {
    override fun getItem(position: Int): Photo = items[position]
    fun replace(items: ArrayList<Photo>) {
        if (items.isEmpty()) return
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BaseViewHolder {
        val binding: com.linhtt.photoeditor.databinding.ItemListFolderBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.item_list_folder, p0, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ViewDataBinding) : BaseViewHolder(binding.root) {
        override fun onBindingData(position: Int) {
            (binding as com.linhtt.photoeditor.databinding.ItemListFolderBinding).viewModel = getItem(position)
        }
    }
}