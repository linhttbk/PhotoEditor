package com.linhtt.photoeditor.view.text

import android.support.v4.app.FragmentManager
import com.example.core.BaseViewModel
import com.linhtt.photoeditor.adapter.ColorAdapter
import com.linhtt.photoeditor.adapter.EditTextPagerAdapter
import com.linhtt.photoeditor.adapter.FontAdapter

class FontColorViewModel(val fragmentManager: FragmentManager):BaseViewModel() {
    var editTextPagerAdapter = EditTextPagerAdapter(fragmentManager)
    fun loadData(){
        editTextPagerAdapter = EditTextPagerAdapter(fragmentManager)

    }
}