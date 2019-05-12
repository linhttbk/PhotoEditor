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
        result.add(Adjustment(R.drawable.icon_hue, R.drawable.icon_hue_active, R.string.adjust_hue_lbl))
        result.add(Adjustment(R.drawable.icon_sharpen, R.drawable.icon_sharpen_active, R.string.adjust_sharpen_lbl))
        result.add(Adjustment(R.drawable.icon_pixel, R.drawable.icon_pixel_active,R.string.adjust_pixel_lbl))
        result.add(Adjustment(R.drawable.icon_vignette, R.drawable.icon_vignette_active,R.string.adjust_vignette_lbl))
        result.add(Adjustment(R.drawable.icon_swirl, R.drawable.icon_swirl_active,R.string.adjust_swirl_lbl))
        return result
    }

    fun selectedFilter(filter: GPUImageFilter, shareViewModel: EditorViewModel) {
         shareViewModel.addFilterToGroup(filter)
    }

    fun adjust(percent: Int,shareViewModel: EditorViewModel): Boolean {
        return shareViewModel.adjust(percent)
    }

    fun updateAdjustUser(shareViewModel: EditorViewModel, percent: Int) {
            shareViewModel.updateAdjustUser(percent)
    }
}