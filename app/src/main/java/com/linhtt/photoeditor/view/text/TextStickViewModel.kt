package com.linhtt.photoeditor.view.text

import android.util.Log
import android.view.View
import com.example.core.BaseRecycleViewAdapter
import com.example.core.BaseViewModel
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.adapter.ColorAdapter
import com.linhtt.photoeditor.adapter.FontAdapter
import com.linhtt.photoeditor.data.model.ColorText
import com.linhtt.photoeditor.data.model.Font

class TextStickViewModel(val text:String): BaseViewModel(){
    var fontAdapter:FontAdapter = FontAdapter(initFontItems())
    var colorAdapter:ColorAdapter = ColorAdapter(initColorItems())

    fun loadData(){
        fontAdapter = FontAdapter(initFontItems())
        colorAdapter = ColorAdapter(initColorItems())
    }
    private fun initFontItems():ArrayList<Font>{
        val results = ArrayList<Font>()
        results.add(Font("AlexBrush-Regular"))
        results.add(Font("AmaticSC-Regular"))
        results.add(Font("BEBAS"))
        results.add(Font("Blackout-2am"))
        results.add(Font("Blackout-Midnight"))
        results.add(Font("CaviarDreams"))
        results.add(Font("danielbd"))
        results.add(Font("PermanentMarker"))
        results.add(Font("Roboto-Medium"))
        results.add(Font("Roboto-Regular"))
        results.add(Font("Roboto-Thin"))
        results.add(Font("RobotoCondensed-Regular"))
        results.add(Font("SEASRN"))
        return results
    }
    private fun initColorItems():ArrayList<ColorText>{
        val results = ArrayList<ColorText>()
        for(i in 1..200)
            results.add(ColorText(i))
        return results
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("clear","clear")
    }
}