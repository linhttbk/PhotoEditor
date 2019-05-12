package com.linhtt.photoeditor.view.filter

import com.example.core.BaseViewModel
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.adapter.FilterColorAdapter
import com.linhtt.photoeditor.custom.GPUImageFilterTools
import com.linhtt.photoeditor.data.model.FilterColor
import com.linhtt.photoeditor.data.model.FilterColor.TypeFilter.*
import com.linhtt.photoeditor.view.editor.EditorViewModel


class FilterViewModel : BaseViewModel() {
    val adapter = FilterColorAdapter(initFilterData())

    private fun initFilterData(): ArrayList<FilterColor> {
        val results = ArrayList<FilterColor>()
        results.add(FilterColor(R.drawable.icon_blend, "Nature", TYPE_NATURE_1))
        results.add(FilterColor(R.drawable.icon_blend, "Nature 2", TYPE_NATURE_2))
        results.add(FilterColor(R.drawable.icon_blend, "Nature 3", TYPE_NATURE_3))
        results.add(FilterColor(R.drawable.icon_blend, "Nature 4", TYPE_NATURE_4))
        results.add(FilterColor(R.drawable.icon_blend, "Nature 5", TYPE_NATURE_5))
        results.add(FilterColor(R.drawable.icon_blend, "Hot", TYPE_HOT))
        results.add(FilterColor(R.drawable.icon_blend, "Cold", TYPE_COLD))
        results.add(FilterColor(R.drawable.icon_blend, "Sepium", TYPE_SEPIUM))
        results.add(FilterColor(R.drawable.icon_blend, "Black White", TYPE_BLACK_WHITE))
        results.add(FilterColor(R.drawable.icon_blend, "Dark", TYPE_DARK))
        results.add(FilterColor(R.drawable.icon_blend, "Yellow", TYPE_YELLOW))
        results.add(FilterColor(R.drawable.icon_blend, "Milk", TYPE_MILK))
        return results
    }

    fun adjustColorMatrix(shareViewModel: EditorViewModel,matrix: FloatArray, offset: FloatArray):Boolean{
        return shareViewModel.adjust(matrix,offset)
    }
}