package com.linhtt.photoeditor.view.home

import android.arch.lifecycle.MutableLiveData
import com.example.core.BaseViewModel

class HomeViewModel : BaseViewModel(), CacheChangeListener {
    var editorList: ArrayList<String>
    var currentIndex: MutableLiveData<Int>

    init {
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


    fun loadBitmapFromCache(): String? {
        if (editorList.isEmpty() || currentIndex.value == -1 || editorList.size <= currentIndex.value!!) return null
        return editorList[currentIndex.value!!]
    }


     override fun onCleared() {
        super.onCleared()
        editorList.clear()

    }
    fun clear(){
        onCleared()
    }

}