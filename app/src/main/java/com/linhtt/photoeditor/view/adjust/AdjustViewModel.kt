package com.linhtt.photoeditor.view.adjust

import com.example.core.BaseViewModel
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.adapter.AdjustmentAdapter
import com.linhtt.photoeditor.custom.GPUImageFilterTools
import com.linhtt.photoeditor.data.model.Adjustment
import com.linhtt.photoeditor.view.editor.EditorViewModel
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter

class AdjustViewModel : BaseViewModel() {
    val adapter = AdjustmentAdapter(initFilter2())
    var currentFilter: GPUImageFilter? = null
    var filterAdjuster: GPUImageFilterTools.FilterAdjuster? = null

    private fun initFilter2(): ArrayList<Adjustment> {
        val result = ArrayList<Adjustment>()
        result.add(
            Adjustment(
                R.drawable.icon_lightness,
                R.drawable.icon_lightness_active,
                R.string.adjust_brightness_lbl
            )
        )
        result.add(Adjustment(R.drawable.icon_contrast, R.drawable.icon_contrast_active, R.string.adjust_contrast_lbl))
        result.add(Adjustment(R.drawable.icon_warmth, R.drawable.icon_warmth_active, R.string.adjust_warm_lbl))
        result.add(
            Adjustment(
                R.drawable.icon_saturation,
                R.drawable.icon_saturation_active,
                R.string.adjust_saturation_lbl
            )
        )
        return result
    }

    fun selectedFilter(filter: GPUImageFilter, shareViewModel: EditorViewModel) {
        currentFilter = shareViewModel.addFilterToGroup(filter)
        filterAdjuster = GPUImageFilterTools.FilterAdjuster(currentFilter!!)
    }

    fun adjust(percent: Int): Boolean {
        if (filterAdjuster != null && filterAdjuster!!.canAdjust()) {
            filterAdjuster!!.adjust(percent)
            return true
        }
        return false
    }

    fun updateAdjustUser(shareViewModel: EditorViewModel, percent: Int) {
        if (currentFilter != null) {
            shareViewModel.listFilter[currentFilter!!.javaClass.name] = percent
        }
    }
}