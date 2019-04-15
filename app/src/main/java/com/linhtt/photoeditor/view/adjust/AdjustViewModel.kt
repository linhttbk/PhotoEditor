package com.linhtt.photoeditor.view.adjust

import com.example.core.BaseViewModel
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.adapter.AdjustmentAdapter
import com.linhtt.photoeditor.data.model.Adjustment

class AdjustViewModel : BaseViewModel() {
    val adapter = AdjustmentAdapter(initFilter2())

    private fun initFilter2(): ArrayList<Adjustment> {
        val result = ArrayList<Adjustment>()
        result.add(Adjustment(R.drawable.icon_lightness, R.string.adjust_brightness_lbl))
        result.add(Adjustment(R.drawable.icon_contrast, R.string.adjust_contrast_lbl))
        result.add(Adjustment(R.drawable.icon_warmth, R.string.adjust_warm_lbl))
        result.add(Adjustment(R.drawable.icon_saturation, R.string.adjust_saturation_lbl))
        return result
    }
}