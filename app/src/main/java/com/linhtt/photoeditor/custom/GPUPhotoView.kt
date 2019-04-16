package com.linhtt.photoeditor.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.sticker.Sticker
import com.sticker.StickerView
import jp.co.cyberagent.android.gpuimage.GPUImageView

class GPUPhotoView : GPUImageView {
    var stickerView: StickerView = StickerView(context)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        stickerView.layoutParams = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(stickerView)
    }

    fun showStickerView() {
        stickerView.visibility = View.VISIBLE
        Log.e("Sticker",""+stickerView.stickerCount.toString())
    }

    fun hideStickerView() {
        stickerView.visibility = View.GONE
    }

    fun addSticker(sticker: Sticker) {
        stickerView.addSticker(sticker)
    }

    fun setStickerTouch(onStickerOperationListener: StickerView.OnStickerOperationListener) {
        stickerView.onStickerOperationListener = onStickerOperationListener
    }

    fun removeSticker() {
        stickerView.removeCurrentSticker()
    }

    fun removeAllStickers() {
        stickerView.removeAllStickers()
    }

    fun fipCurrentSticker(direction: Int) {
        stickerView.flipCurrentSticker(direction)
    }
}