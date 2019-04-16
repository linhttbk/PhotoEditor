package com.linhtt.photoeditor.view.sticker

import android.support.v4.app.FragmentManager
import com.example.core.BaseViewModel
import com.linhtt.photoeditor.adapter.StickerAdapter
import com.linhtt.photoeditor.adapter.StickerPagerAdapter

class StickerViewModel(fragmentManager: FragmentManager):BaseViewModel() {
    val stickerAdapter = StickerPagerAdapter(fragmentManager)
}