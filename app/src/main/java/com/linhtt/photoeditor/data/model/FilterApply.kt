package com.linhtt.photoeditor.data.model

import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter

class FilterApply(val filter: GPUImageFilter, val percent: Int) {
    fun getFilterClass(): String = filter::class.toString()
}