package com.linhtt.photoeditor.view.editor

import android.arch.lifecycle.MutableLiveData
import android.graphics.BitmapFactory
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.View
import com.example.core.BaseViewModel
import com.example.core.ext.rx.AndroidSchedulerProvider
import com.example.core.ext.rx.fromComputation
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.adapter.FilterAdapter
import com.linhtt.photoeditor.adapter.StickerPagerAdapter
import com.linhtt.photoeditor.custom.GPUImageFilterTools
import com.linhtt.photoeditor.data.model.Filter
import com.linhtt.photoeditor.data.model.FilterApply
import com.linhtt.photoeditor.view.home.CacheChangeListener
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilterGroup
import jp.co.cyberagent.android.gpuimage.filter.GPUImageTwoInputFilter


class EditorViewModel(val path: String, fragmentManager: FragmentManager) : BaseViewModel(), CacheChangeListener {
    var editorList: ArrayList<String>
    var currentIndex: MutableLiveData<Int>

    val adapter = FilterAdapter(initFilter())
    val adapter2 = FilterAdapter(initFilter2())
    val stickerPagerAdapter = StickerPagerAdapter(fragmentManager)
    var visibility: MutableLiveData<Int> = MutableLiveData()
    var listFilter: HashMap<String, Int> = HashMap()
    var listApply: ArrayList<FilterApply> = ArrayList()
    val group = GPUImageFilterGroup()
    var currentFilter: GPUImageFilter? = null
    var filterAdjuster: GPUImageFilterTools.FilterAdjuster? = null
    var dispose: Disposable? = null

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
      //  result.add(Filter(R.drawable.icon_filter, R.string.lbl_filter))
        result.add(Filter(R.drawable.ic_icon_adjust, R.string.lbl_adjust))
        result.add(Filter(R.drawable.icon_blur_circle, R.string.lbl_blur))
        // result.add(Filter(R.drawable.icon_bright, R.string.lbl_brightness))
        //result.add(Filter(R.drawable.icon_blend, R.string.lbl_blend))
        //  result.add(Filter(R.drawable.icon_mirror, R.string.lbl_mirror))
        //  result.add(Filter(R.drawable.icon_fit, R.string.lbl_position))
        result.add(Filter(R.drawable.icon_sticker, R.string.lbl_sticker))
        //result.add(Filter(R.drawable.icon_body_tattoo, R.string.lbl_body_tatoo))
        result.add(Filter(R.drawable.icon_text, R.string.lbl_add_text))
        result.add(Filter(R.drawable.icon_draw, R.string.lbl_draw))
        result.add(Filter(R.drawable.icon_edge, R.string.lbl_edge))
        result.add(Filter(R.drawable.icon_shadow, R.string.lbl_shadow))
        //  result.add(Filter(R.drawable.icon_add, R.string.lbl_add_image))
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


    private fun findFilterInGroup(filterGroup: GPUImageFilterGroup, filterType: Class<*>): GPUImageFilter? {
        for (filter in filterGroup.filters) {
            if (filterType.isInstance(filter)) {
                Log.e("findFilter", "Found $filterType")
                return filter
            }
        }

        return null
    }

    fun getFilterGroup(): GPUImageFilterGroup {
        val items = ArrayList<GPUImageFilter>()
        items.add(GPUImageFilter())
        for (filter in listApply) {
            items.add(filter.filter)
        }
        return GPUImageFilterGroup(items)
    }

    private fun findFilterInGroup(filterType: Class<*>): GPUImageFilter? {
        for (filter in listApply) {
            if (filterType.isInstance(filter.filter)) {
                Log.e("findFilter", "Found $filterType")
                return filter.filter
            }
        }

        return null
    }

    fun addFilterToGroup(filter: GPUImageFilter, defValue: Int = 50) {
        val result = findFilterInGroup(filter.javaClass)
        if (result == null) {
            listApply.add(FilterApply(filter, defValue))
            // group.addFilter(filter)
            listFilter[filter.javaClass.name] = defValue
            currentFilter = filter
            Log.e("filter", "add new")
        } else {
            Log.e("filter", "added")
            currentFilter = result
        }
        updateAdjustFilter(currentFilter!!)

    }

    fun removeBlurFilter(filterType: Class<*>) {
        for (item in listApply) {
            if (filterType.isInstance(item.filter)) {
                Log.e("Remove","removed")
                listApply.remove(item)
                listFilter.remove(item.getFilterClass())
            }
        }
    }

    fun addBlendFilter(filter: GPUImageTwoInputFilter, defValue: Int = 50, currentPath: String, adjust: Unit) {
        dispose = Observable.fromCallable {
            BitmapFactory.decodeFile(currentPath)
        }.fromComputation(AndroidSchedulerProvider())
            .subscribe({
                filter.apply {
                    bitmap = it
                    listApply.add(FilterApply(this, 50))
                    listFilter[filter.javaClass.name] = defValue
                    currentFilter = filter
                    updateAdjustFilter(currentFilter!!)
                    adjust

                    Log.e("filter", filter.javaClass.name)
                }
            }, {
                it.printStackTrace()
            })
    }

    private fun updateAdjustFilter(filter: GPUImageFilter) {
        filterAdjuster = GPUImageFilterTools.FilterAdjuster(filter)
    }

    fun updateAdjustUser(percent: Int) {
        if (currentFilter == null) return
        listFilter[currentFilter!!.javaClass.name] = percent

    }

    fun adjust(percent: Int): Boolean {
        if (filterAdjuster != null && filterAdjuster!!.canAdjust()) {
            filterAdjuster?.adjust(percent)
            return true
        }
        return false
    }

    fun adjust(matrix: FloatArray, offset: FloatArray): Boolean {
        if (filterAdjuster != null && filterAdjuster!!.canAdjust()) {
            filterAdjuster?.adjust(matrix, offset)
            return true
        }
        return false
    }
}