package com.linhtt.photoeditor.data.model

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.core.bitmap.BitmapUtil
import com.example.core.ext.rx.AndroidSchedulerProvider
import com.example.core.ext.rx.fromComputation
import com.example.core.ext.view.loadImage
import com.linhtt.photoeditor.util.Util
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.GPUImageTwoInputFilter


data class Blend(
    val title: Int,
    val filter: GPUImageTwoInputFilter?,
    var percent: Int = 50,
    var selected: Boolean = false,
    var reloadData: Boolean = true
) {

    constructor() : this(0, null, 0, false) {

    }


    var disposable: Disposable? = null


    private fun loadBitmapFromPath(path: String): Observable<Bitmap> {
        return Observable.fromCallable {
            BitmapUtil.decodeSampledBitmapFromResource(path, 60, 60)
        }.fromComputation(AndroidSchedulerProvider())
    }

    fun loadBitmapWithFilterApplied(imv: ImageView, path: String, blendPath: String, filter: GPUImageTwoInputFilter) {
        disposable = loadBitmapFromPath(path).zipWith(loadBitmapFromPath(blendPath),
            object : BiFunction<Bitmap, Bitmap, Bitmap> {
                override fun apply(t1: Bitmap, t2: Bitmap): Bitmap {
                    val gpuImage = GPUImage(imv.context)
                    gpuImage.setImage(t1)
                    filter.apply {
                        bitmap = t2
                    }
                    val result = gpuImage.getBitmapWithFilter(filter)
                    t1.recycle()
                    t2.recycle()
                    return result
                }

            }).fromComputation(AndroidSchedulerProvider()).subscribe({
            imv.loadImage(it).apply(
                RequestOptions().transform(
                    CenterCrop(),
                    RoundedCorners(Util.convertDpToPixel(5f, imv.context).toInt())
                )
            ).into(imv)
        }, {
            it.printStackTrace()
        })

    }


    fun clear() {
        if (disposable != null) {
            disposable?.dispose()
        }
    }
}