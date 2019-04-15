package com.linhtt.photoeditor.view.editor

import android.arch.lifecycle.MutableLiveData
import android.support.v4.app.FragmentManager
import android.view.View
import com.example.core.BaseViewModel
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.adapter.FilterAdapter
import com.linhtt.photoeditor.adapter.StickerPagerAdapter
import com.linhtt.photoeditor.data.model.Filter
import com.linhtt.photoeditor.view.home.CacheChangeListener


class EditorViewModel(val path: String,fragmentManager: FragmentManager) : BaseViewModel(), CacheChangeListener {
    var editorList: ArrayList<String>
    var currentIndex: MutableLiveData<Int>

    val adapter = FilterAdapter(initFilter())
    val adapter2 = FilterAdapter(initFilter2())
    val stickerPagerAdapter = StickerPagerAdapter(fragmentManager)
    var visibility: MutableLiveData<Int> = MutableLiveData()

    init {
        visibility.value = View.INVISIBLE
        editorList = ArrayList()
        currentIndex = MutableLiveData()
        currentIndex.value = -1
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
    override fun undo() {
        try {
            if (editorList.size < 2) return
            if (currentIndex.value == null || currentIndex.value!! <= 0) return
            currentIndex.value = currentIndex.value!! - 1

        } catch (exception: Exception) {
            exception.printStackTrace()
        }

    }

    override fun redo() {
        try {
            if (editorList.size < 2) return
            if (currentIndex.value == null || currentIndex.value!! >= editorList.size - 1) return
            currentIndex.value = currentIndex.value!! + 1

        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    override fun addToMemoryCache(filePath: String) {
        editorList.add(filePath)
        currentIndex.value = editorList.size - 1
    }


    fun loadBitmapFromCache(): String {
        if (editorList.isEmpty() || currentIndex.value == -1 || editorList.size <= currentIndex.value!!) return path
        return editorList[currentIndex.value!!]
    }


    override fun onCleared() {
        super.onCleared()
        editorList.clear()

    }
    fun clear(){
        onCleared()
    }



}