package com.linhtt.photoeditor.view.crop

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.example.core.BaseViewModel
import com.linhtt.photoeditor.MainApplication
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.adapter.RatioAdapter
import com.linhtt.photoeditor.data.model.Ratio

class CropViewModel(val path: String) : BaseViewModel() {
    var adapter : RatioAdapter
    init {
        adapter = RatioAdapter(initCropRatio())
    }
    private fun initCropRatio() :ArrayList<Ratio>{
        val items = ArrayList<Ratio>()
        items.add(Ratio(R.string.ratio_free, R.drawable.ratio_1_1,R.drawable.ratio_1_1_click,Ratio.RatioType.RATIO1_1))
        items.add(Ratio(R.string.ratio_12, R.drawable.ratio_1_2,R.drawable.ratio_1_2_click,Ratio.RatioType.RATIO1_2))
        items.add(Ratio(R.string.ratio_23, R.drawable.ratio_2_3,R.drawable.ratio_2_3_click,Ratio.RatioType.RATIO2_3))
        items.add(Ratio(R.string.ratio_32, R.drawable.ratio_3_2,R.drawable.ratio_3_2_click,Ratio.RatioType.RATIO3_2))
        items.add(Ratio(R.string.ratio_34, R.drawable.ratio_3_4,R.drawable.ratio_3_4_click,Ratio.RatioType.RATIO3_4))
        items.add(Ratio(R.string.ratio_43, R.drawable.ratio_4_3,R.drawable.ratio_4_3_click,Ratio.RatioType.RATIO4_3))
        items.add(Ratio(R.string.ratio_45, R.drawable.ratio_4_5,R.drawable.ratio_4_5_click,Ratio.RatioType.RATIO4_5))
        items.add(Ratio(R.string.ratio_54, R.drawable.ratio_5_4,R.drawable.ratio_5_4_click,Ratio.RatioType.RATIO5_4))
        items.add(Ratio(R.string.ratio_916, R.drawable.ratio_9_16,R.drawable.ratio_9_16_click,Ratio.RatioType.RATIO9_16))
        items.add(Ratio(R.string.ratio_169, R.drawable.ratio_16_9,R.drawable.ratio_16_9_click,Ratio.RatioType.RATIO16_9))
        items[0].isSelected = true

        return items
    }


}