package com.linhtt.photoeditor.custom

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.media.MediaScannerConnection
import android.os.Environment
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.sticker.Sticker
import com.sticker.StickerView
import jp.co.cyberagent.android.gpuimage.GPUImageView
import java.io.File
import java.io.FileOutputStream


class GPUPhotoView : GPUImageView {
    var stickerView: StickerDrawView = StickerDrawView(context)
    private var listener: OnPictureSavedListener? = null

    //   var drawingView = DrawingView(context)
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        stickerView.layoutParams = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        //drawingView.layoutParams = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(stickerView)
        // addView(drawingView)
    }


    fun showEditorView(enableDraw: Boolean) {
        stickerView.visibility = View.VISIBLE
        if (enableDraw)
            stickerView.lockAllSticker()
        stickerView.setEnableDraw(enableDraw)

    }

    fun hideEditorView() {
        stickerView.visibility = View.GONE

        //  drawingView.visibility = View.GONE


    }

    fun showStickerView() {
        stickerView.visibility = View.VISIBLE
        // drawingView.isEnabled = false
        Log.e("Sticker", "" + stickerView.stickerCount.toString())
    }

//    fun showDrawView(){
//        drawingView.visibility = View.VISIBLE
//        drawingView.isEnabled = true
//    }
//    fun hideDrawView(){
//        drawingView.visibility = View.GONE
//    }
//    fun setColorDraw(color:Int){
//        drawingView.setColorPaint(color)
//    }

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

    override fun saveToPictures(folderName: String?, fileName: String?, listener: OnPictureSavedListener?) {
        super.saveToPictures(folderName, fileName, listener)
    }

    fun saveBitmapToFile() {
        val background = capture()
        val canvas = Canvas(background)
        canvas.drawBitmap(loadBitmapFromView(), Matrix(), null)
        val path = Environment.getExternalStorageDirectory()
        val file = File(path, "PhotoEditor" + "/" + System.currentTimeMillis().toString() + ".png")
        file.parentFile.mkdir()
        background.compress(Bitmap.CompressFormat.JPEG, 80, FileOutputStream(file))
        MediaScannerConnection.scanFile(
            context,
            arrayOf(file.toString()), null
        ) { _, uri ->
            if (listener != null) {
                handler.post { listener?.onPictureSaved(uri) }
            }
        }
    }
    fun setSaveListener(listener: OnPictureSavedListener){
        this.listener = listener
    }
    private fun loadBitmapFromView(): Bitmap {
        val bitmap = Bitmap.createBitmap(stickerView.measuredWidth, stickerView.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        this.draw(canvas)
        return bitmap
    }
}