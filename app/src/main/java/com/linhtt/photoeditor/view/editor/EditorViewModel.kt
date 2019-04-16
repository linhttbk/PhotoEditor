package com.linhtt.photoeditor.view.editor

import android.arch.lifecycle.MutableLiveData
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.View
import com.example.core.BaseViewModel
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.adapter.FilterAdapter
import com.linhtt.photoeditor.adapter.StickerPagerAdapter
import com.linhtt.photoeditor.custom.GPUImageColorFilter
import com.linhtt.photoeditor.data.model.Filter
import com.linhtt.photoeditor.view.home.CacheChangeListener
import jp.co.cyberagent.android.gpuimage.filter.*


class EditorViewModel(val path: String, fragmentManager: FragmentManager) : BaseViewModel(), CacheChangeListener {
    var editorList: ArrayList<String>
    var currentIndex: MutableLiveData<Int>

    val adapter = FilterAdapter(initFilter())
    val adapter2 = FilterAdapter(initFilter2())
    val stickerPagerAdapter = StickerPagerAdapter(fragmentManager)
    var visibility: MutableLiveData<Int> = MutableLiveData()
    var listFilter: HashMap<String, Int> = HashMap()
    val group = GPUImageFilterGroup()

    init {
        visibility.value = View.INVISIBLE
        editorList = ArrayList()
        currentIndex = MutableLiveData()
        currentIndex.value = -1

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

    fun clear() {
        onCleared()
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

    private fun initGroupFilter(): ArrayList<GPUImageFilter> {
        val result = ArrayList<GPUImageFilter>()
        result.add(GPUImageBrightnessFilter())
        result.add(GPUImageSharpenFilter())
        result.add(GPUImageSepiaToneFilter())
        result.add(GPUImageContrastFilter())
        result.add(GPUImageGammaFilter())
        result.add(GPUImageBrightnessFilter())
        result.add(GPUImageSobelEdgeDetectionFilter())
        result.add(GPUImageThresholdEdgeDetectionFilter())
        result.add(GPUImage3x3ConvolutionFilter())
        result.add(GPUImageEmbossFilter())
        result.add(GPUImage3x3TextureSamplingFilter())
        result.add(GPUImageHueFilter())
        result.add(GPUImagePosterizeFilter())
        result.add(GPUImagePixelationFilter())
        result.add(GPUImageSaturationFilter())
        result.add(GPUImageExposureFilter())
        result.add(GPUImageHighlightShadowFilter())
        result.add(GPUImageMonochromeFilter())
        result.add(GPUImageOpacityFilter())
        result.add(GPUImageRGBFilter())
        result.add(GPUImageWhiteBalanceFilter())
        result.add(GPUImageVignetteFilter())
        result.add(GPUImageLuminanceThresholdFilter())
        result.add(GPUImageDissolveBlendFilter())
        result.add(GPUImageGaussianBlurFilter())
        result.add(GPUImageCrosshatchFilter())
        result.add(GPUImageBulgeDistortionFilter())
        result.add(GPUImageGlassSphereFilter())
        result.add(GPUImageHazeFilter())
        result.add(GPUImageSphereRefractionFilter())
        result.add(GPUImageSwirlFilter())
        result.add(GPUImageColorBalanceFilter())
        result.add(GPUImageLevelsFilter())
        result.add(GPUImageBilateralBlurFilter())
        result.add(GPUImageTransformFilter())
        result.add(GPUImageSolarizeFilter())
        result.add(GPUImageVibranceFilter())
        result.add(GPUImageColorFilter())
        return result
    }

    private fun findFilterInGroup(filterGroup: GPUImageFilterGroup, filterType: Class<*>): GPUImageFilter? {
        for (filter in filterGroup.filters) {
            if (filterType.isInstance(filter)) {
                Log.e("findFilter", "Found $filterType")
                return filter
            }
        }

        return null
    }

    fun addFilterToGroup(filter: GPUImageFilter, defValue: Int = 50): GPUImageFilter {
        val result = findFilterInGroup(group, filter.javaClass)
        if (result == null) {
            group.addFilter(filter)
            listFilter.put(filter.javaClass.name, defValue)
            return filter
        }
        return result
    }
}