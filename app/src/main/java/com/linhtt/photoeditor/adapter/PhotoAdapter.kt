package com.linhtt.photoeditor.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.core.BaseRecycleViewAdapter
import com.linhtt.photoeditor.R
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoAdapter(var items: ArrayList<String>) : BaseRecycleViewAdapter<String>() {

    fun replace(items: ArrayList<String>) {
        if (items.isEmpty()) return
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BaseViewHolder {
        val binding: com.linhtt.photoeditor.databinding.ItemPhotoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.item_photo, p0, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun getItem(position: Int): String = items[position]
    inner class ViewHolder(private val binding: ViewDataBinding) : BaseViewHolder(binding.root) {
        init {
            val params = binding.root.imvPhoto.layoutParams as LinearLayout.LayoutParams
            val displayMetrics = binding.root.context.resources.displayMetrics
            val width = displayMetrics.widthPixels
            params.height = width / 4 - 4 * 5
            binding.root.imvPhoto.layoutParams = params
        }

        override fun onBindingData(position: Int) {
            (binding as com.linhtt.photoeditor.databinding.ItemPhotoBinding).path = getItem(position)
        }
    }
}