package com.linhtt.photoeditor.custom

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.MotionEvent
import com.sticker.StickerView

class StickerDrawView(context: Context) : StickerView(context) {
    private var mBitmap: Bitmap? = null
    private var mCanvas: Canvas? = null
    private val mPath: Path
    private val mBitmapPaint: Paint
    private var colorCode: Int = Color.WHITE
    private var mPaint: Paint
    private var mX: Float = 0.toFloat()
    private var mY: Float = 0.toFloat()
    private val TOUCH_TOLERANCE = 4f
    private var drawEnable = false
    private var eraserMode = false

    init {
        mPath = Path()
        mBitmapPaint = Paint(Paint.DITHER_FLAG)
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.color = colorCode
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeWidth = 12f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)

        mCanvas = Canvas(mBitmap)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        if (eraserMode) {
            mPaint.color = ContextCompat.getColor(context, android.R.color.transparent)
            mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        } else {
            mPaint.color = colorCode
            mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
        }
        canvas?.drawBitmap(mBitmap, 0f, 0f, mBitmapPaint)
        canvas?.drawPath(mPath, mPaint)
    }

    private fun touchStart(x: Float, y: Float) {
        mPath.reset()
        mPath.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun touchUp() {
        mPath.lineTo(mX, mY)
        mCanvas?.drawPath(mPath, mPaint)
        mPath.reset()
    }

    private fun touchMove(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (mX + x) / 2, (mY + y) / 2)
            mX = x
            mY = y
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!drawEnable)
            return super.onTouchEvent(event)
        Log.e("touch", "touch")
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }

        return true
    }

    fun setEnableDraw(enableDraw: Boolean) {
        drawEnable = enableDraw
    }

    fun setDrawColor(color: Int) {
        this.colorCode = color
    }

    fun setSizeDraw(size: Float) {
        mPaint.strokeWidth = size
    }

    fun changeEraserMode(eraser:Boolean) {
        this.eraserMode = eraser
    }
}