package com.linhtt.photoeditor.view.home

import android.graphics.Bitmap

interface CacheChangeListener {
    fun undo()
    fun redo()
    fun addToMemoryCache(filePath: String)

}