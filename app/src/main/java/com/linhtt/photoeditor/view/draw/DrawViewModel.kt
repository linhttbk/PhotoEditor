package com.linhtt.photoeditor.view.draw

import android.support.v4.app.FragmentManager
import com.example.core.BaseViewModel
import com.linhtt.photoeditor.adapter.ColorAdapter
import com.linhtt.photoeditor.adapter.DrawPagerAdapter
import com.linhtt.photoeditor.data.model.ColorText

class DrawViewModel(fragmentManager: FragmentManager) : BaseViewModel() {
    val colorAdapter = ColorAdapter(initColorItems())
    val pagerAdapter = DrawPagerAdapter(fragmentManager)


    private fun initColorItems(): ArrayList<ColorText> {
        val results = ArrayList<ColorText>()
        for (i in 1..200)
            results.add(ColorText(i))
        return results
    }
}