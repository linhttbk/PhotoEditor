package com.linhtt.photoeditor.adapter

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.core.BaseRecycleViewAdapter
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.data.model.StickerData
import kotlinx.android.synthetic.main.item_sticker.view.*

class StickerAdapter(var items: ArrayList<StickerData>) : BaseRecycleViewAdapter<StickerData>() {
    override fun getItem(position: Int): StickerData = items[position]
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<com.linhtt.photoeditor.databinding.ItemStickerBinding>(
            LayoutInflater.from(p0.context),
            R.layout.item_sticker,
            p0,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: com.linhtt.photoeditor.databinding.ItemStickerBinding) :
        BaseViewHolder(binding.root) {

        init {
            val params = binding.root.imvSticker.layoutParams as LinearLayout.LayoutParams
            val displayMetrics = binding.root.context.resources.displayMetrics
            val width = displayMetrics.widthPixels
            params.height = width / 7 - 7 * 10
            binding.root.imvSticker.layoutParams = params
        }

        override fun onBindingData(position: Int) {
            binding.viewModel = getItem(position)
            binding.executePendingBindings()
        }

    }
}