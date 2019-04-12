package com.linhtt.photoeditor.view.editor

import android.arch.lifecycle.MutableLiveData
import android.support.v4.app.FragmentManager
import android.view.View
import com.example.core.BaseViewModel
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.adapter.FilterAdapter
import com.linhtt.photoeditor.adapter.StickerPagerAdapter
import com.linhtt.photoeditor.data.model.Filter


class EditorViewModel(val path: String,fragmentManager: FragmentManager) : BaseViewModel() {
    val adapter = FilterAdapter(initFilter())
    val adapter2 = FilterAdapter(initFilter2())
    val stickerPagerAdapter = StickerPagerAdapter(fragmentManager)
    var visibility: MutableLiveData<Int> = MutableLiveData()

    init {
        visibility.value = View.INVISIBLE
    }

    private fun initFilter(): ArrayList<Filter> {
        val result = ArrayList<Filter>()
        result.add(Filter(R.drawable.icon_crop, R.string.lbl_crop))
        result.add(Filter(R.drawable.icon_filter, R.string.lbl_filter))
        result.add(Filter(R.drawable.ic_icon_adjust, R.string.lbl_adjust))
        result.add(Filter(R.drawable.icon_blur_circle, R.string.lbl_blur))
        result.add(Filter(R.drawable.icon_bright, R.string.lbl_brightness))
        result.add(Filter(R.drawable.icon_blend, R.string.lbl_blend))
        result.add(Filter(R.drawable.icon_mirror, R.string.lbl_mirror))
        result.add(Filter(R.drawable.icon_fit, R.string.lbl_position))
        result.add(Filter(R.drawable.icon_sticker, R.string.lbl_sticker))
        result.add(Filter(R.drawable.icon_body_tattoo, R.string.lbl_body_tatoo))
        result.add(Filter(R.drawable.icon_text, R.string.lbl_add_text))
        result.add(Filter(R.drawable.icon_draw, R.string.lbl_draw))
        result.add(Filter(R.drawable.icon_add, R.string.lbl_add_image))
        return result
    }
    private fun initFilter2(): ArrayList<Filter> {
        val result = ArrayList<Filter>()
        result.add(Filter(R.drawable.ic_icon_adjust, R.string.lbl_adjust))
        result.add(Filter(R.drawable.icon_blur_circle, R.string.lbl_blur))
        result.add(Filter(R.drawable.icon_bright, R.string.lbl_brightness))
        result.add(Filter(R.drawable.icon_blend, R.string.lbl_blend))
        result.add(Filter(R.drawable.icon_mirror, R.string.lbl_mirror))
        result.add(Filter(R.drawable.icon_fit, R.string.lbl_position))
        result.add(Filter(R.drawable.icon_sticker, R.string.lbl_sticker))
        result.add(Filter(R.drawable.icon_body_tattoo, R.string.lbl_body_tatoo))
        result.add(Filter(R.drawable.icon_text, R.string.lbl_add_text))
        result.add(Filter(R.drawable.icon_draw, R.string.lbl_draw))
        result.add(Filter(R.drawable.icon_add, R.string.lbl_add_image))
        return result
    }

    override fun onCleared() {
        super.onCleared()
    }


}