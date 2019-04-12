package com.linhtt.photoeditor.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageView
import io.reactivex.disposables.Disposable
import android.graphics.Bitmap
import com.example.core.bitmap.BitmapUtil


class CropImageView : ImageView {
    private var paint = Paint()
    var disposable: Disposable? = null
    var currentBitmap: Bitmap? = null
    private val rect = Rect()
    private var mImageRect = RectF()
    private var mFrameRect = RectF()
    private var mInitialFrameRect: RectF? = null
    private var mCenter = PointF()
    private var mMatrix: Matrix
    var mScale = 1.0f
    var mViewWidth = 0
    var mViewHeight = 0
    var xTranslation = 0f
    var yTranslation = 0f
    private var mAngle = 0.0f
    private var mImgWidth = 0.0f
    private var mImgHeight = 0.0f
    private var mIsInitialized: Boolean = false

    private var mPaintBitmap: Paint
    private var mPaintFrame: Paint

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {

    }

    constructor(context: Context, attributeSet: AttributeSet?, theme: Int) : super(context, attributeSet, theme)


    init {
        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        paint.isAntiAlias = true
        adjustViewBounds = true
        scaleType = ScaleType.CENTER_CROP
        mMatrix = Matrix()
        mPaintBitmap = Paint()
        mPaintFrame = Paint()
        mPaintBitmap.isFilterBitmap = true

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawRect(canvas!!)
        if (currentBitmap != null) {
            Log.e("Size", currentBitmap!!.width.toString() + currentBitmap!!.height.toString() + width + height)
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        val viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(viewWidth, viewHeight)
        mViewWidth = viewWidth - paddingLeft - paddingRight;
        mViewHeight = viewHeight - paddingTop - paddingBottom;

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (currentBitmap != null)
            setupLayout(mViewWidth, mViewHeight)

    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rect.left = 100
        rect.right = w - 100
        rect.top = 100
        rect.bottom = h - 100
        mImageRect = RectF(0f, 0f, mViewWidth.toFloat(), mViewHeight.toFloat())
    }

    private fun drawRect(canvas: Canvas) {

        if (mIsInitialized) {
            setMatrix()
            drawFrame(canvas)
        }

//        val frameRectF = computeFrameCropRect()
//        paint.style = Paint.Style.STROKE
//        canvas.drawRect(frameRectF, paint)
//        paint.style = Paint.Style.FILL
//        canvas.drawCircle(frameRectF.left, frameRectF.top, 20f, paint)
//        canvas.drawCircle(frameRectF.right, frameRectF.top, 20f, paint)
//        canvas.drawCircle(frameRectF.left, frameRectF.bottom, 20f, paint)
//        canvas.drawCircle(frameRectF.right, frameRectF.bottom, 20f, paint)
//        val widthOverlay = rect.width() / 3
//        val heightOverlay = rect.height() / 3
//        val x1: Float = (widthOverlay + rect.left).toFloat()
//        val x2: Float = (rect.right - widthOverlay).toFloat()
//
//        canvas.drawLine(x1, rect.top + x1, rect.top + x1, widthOverlay + x1)
//        canvas.drawLine(x1 + widthOverlay,)

    }

    private fun drawFrame(canvas: Canvas) {
        mPaintFrame.setAntiAlias(true)
        mPaintFrame.setFilterBitmap(true)
        mPaintFrame.setStyle(Paint.Style.STROKE)
        mPaintFrame.setColor(Color.WHITE)
        canvas.drawRect(mFrameRect, mPaintFrame)
    }

    private fun setupLayout(viewW: Int, viewH: Int) {
        if (viewW == 0 || viewH == 0) return
        setCenter(PointF(paddingLeft + viewW * 0.5f, paddingTop + viewH * 0.5f))
        setScale(calcScale(viewW.toFloat(), viewH.toFloat(), mAngle))
        setMatrix()
        mImageRect = calcImageRect(RectF(0f, 0f, mImgWidth, mImgHeight), mMatrix)
        mFrameRect = calcFrameRect(mImageRect)
        mIsInitialized = true
        invalidate()
    }

    private val mInitialFrameScale: Float = 0.75f


    fun loadBitmap(filePath: String) {
        var reWidth = 0
        var reHeight = 0
        reWidth = if (mViewWidth == 0) getDensity().widthPixels else mViewWidth

        reHeight = if (mViewHeight == 0) getDensity().heightPixels else mViewHeight
        Log.e("SizeRe", "$reWidth  and $reHeight")
       // val bitmap = scaleBitmap(BitmapFactory.decodeFile(filePath), reWidth, reHeight)
        val bitmap = BitmapUtil.decodeSampledBitmapFromResource(filePath,reWidth,reHeight)
//        val bitmap =
//            BitmapUtil.getScaledDownBitmap(BitmapFactory.decodeFile(filePath),Math.max(reWidth,reHeight),true)
        setImageBitmap(bitmap)
    }

    fun scaleBitmap(bitmap: Bitmap, reqWidth: Int, reqHeight: Int): Bitmap {
        val background = Bitmap.createBitmap(reqWidth, reqHeight, Bitmap.Config.ARGB_8888)

        val originalWidth = bitmap.width.toFloat()
        val originalHeight = bitmap.height.toFloat()

        val canvas = Canvas(background)

        val scale = reqWidth / originalWidth

        xTranslation = 0.0f
        yTranslation = (reqHeight - originalHeight * scale) / 2.0f

        val transformation = Matrix()
        transformation.postTranslate(xTranslation, yTranslation)
        transformation.preScale(scale, scale)

        val paint = Paint()
        paint.isFilterBitmap = true

        canvas.drawBitmap(bitmap, transformation, paint)

        return background
    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        currentBitmap = bm
    }


    private fun calcImageRect(rect: RectF, matrix: Matrix): RectF {
        val applied = RectF()
        matrix.mapRect(applied, rect)
        return applied
    }

    private fun setMatrix() {
        mMatrix.reset()
        mMatrix.setTranslate(mCenter.x - mImgWidth * 0.5f, mCenter.y - mImgHeight * 0.5f)
        mMatrix.postScale(mScale, mScale, mCenter.x, mCenter.y)
        mMatrix.postRotate(mAngle, mCenter.x, mCenter.y)
    }

    private fun calcFrameRect(imageRect: RectF): RectF {

        val imgRatio = imageRect.width() / imageRect.height()
        val frameRatio = 1
        var l = imageRect.left
        var t = imageRect.top
        var r = imageRect.right
        var b = imageRect.bottom
        if (frameRatio >= imgRatio) {
            l = imageRect.left
            r = imageRect.right
            val hy = (imageRect.top + imageRect.bottom) * 0.5f
            val hh = imageRect.width() / frameRatio * 0.5f
            t = hy - hh
            b = hy + hh
        } else if (frameRatio < imgRatio) {
            t = imageRect.top
            b = imageRect.bottom
            val hx = (imageRect.left + imageRect.right) * 0.5f
            val hw = imageRect.height() * frameRatio * 0.5f
            l = hx - hw
            r = hx + hw
        }
        val w = r - l
        val h = b - t
        val cx = l + w / 2
        val cy = t + h / 2
        val sw = w * mInitialFrameScale
        val sh = h * mInitialFrameScale
        return RectF(cx - sw / 2, cy - sh / 2, cx + sw / 2, cy + sh / 2)
    }

    private fun calcScale(viewW: Float, viewH: Float, angle: Float): Float {
        mImgWidth = currentBitmap!!.width.toFloat()
        mImgHeight = currentBitmap!!.height.toFloat()
        if (mImgWidth <= 0) mImgWidth = viewW
        if (mImgHeight <= 0) mImgHeight = viewH
        val viewRatio = viewW / viewH

        val imgRatio = getRotatedWidth(angle) / getRotatedWidth(angle)
        var scale = 1.0f
        if (imgRatio >= viewRatio) {
            scale = viewW / getRotatedWidth(angle)
        } else if (imgRatio < viewRatio) {
            scale = viewH / getRotatedHeight(angle)
        }
        return scale
    }

    private fun getRotatedWidth(angle: Float): Float {
        return getRotatedWidth(angle, mImgWidth, mImgHeight)
    }

    private fun getRotatedWidth(angle: Float, width: Float, height: Float): Float {
        return if (angle % 180 == 0f) width else height
    }

    private fun getRotatedHeight(angle: Float): Float {
        return getRotatedHeight(angle, mImgWidth, mImgHeight)
    }

    private fun getRotatedHeight(angle: Float, width: Float, height: Float): Float {
        return if (angle % 180 == 0f) height else width
    }

    private fun getDensity(): DisplayMetrics {
        return resources.displayMetrics
    }

    private fun setCenter(mCenter: PointF) {
        this.mCenter = mCenter
    }

    private fun setScale(mScale: Float) {
        this.mScale = mScale
    }

}