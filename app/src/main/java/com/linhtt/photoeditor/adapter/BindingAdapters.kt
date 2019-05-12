package com.linhtt.photoeditor.adapter

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.core.ext.view.loadImage
import com.google.common.reflect.Reflection.getPackageName
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.data.model.*
import com.linhtt.photoeditor.util.Util
import com.linhtt.photoeditor.view.editor.EditorViewModel
import jp.co.cyberagent.android.gpuimage.GPUImageView
import java.io.File

@BindingAdapter("imgPath")
fun setImagePath(imv: ImageView, path: String) {
    Glide.with(imv.context).load(path).thumbnail(0.5f).into(imv)

}
@BindingAdapter("imgUri")
fun setImageUri(imv: ImageView, uri: Uri) {
    Glide.with(imv.context).load(uri).into(imv)

}

@BindingAdapter("gpuPath")
fun setGpuPath(gpuImageView: GPUImageView, path: String) {
    Log.e("BindingAdapter", path)
    gpuImageView.setImage(File(path))
}

@BindingAdapter("viewModel")
fun setData(gpuImageView: GPUImageView, viewModel: EditorViewModel) {
    val filePath = viewModel.loadBitmapFromCache()
    gpuImageView.setImage(File(filePath))

}

@BindingAdapter("bitmap")
fun setGpuPath(gpuImageView: GPUImageView, bitmap: Bitmap) {
    gpuImageView.setImage(bitmap)
}

@BindingAdapter("iconRes")
fun setIconRes(imv: ImageView, resId: Int) {
    imv.setImageResource(resId)
}

@BindingAdapter("icon")
fun setIcon(imv: ImageView, resId: Int) {
    Glide.with(imv.context).load(resId).into(imv)
}


@BindingAdapter("ratioItem")
fun setIconRes(imv: ImageView, ratio: Ratio) {
    if (ratio.isSelected) imv.setImageResource(ratio.activeIcon)
    else imv.setImageResource(ratio.icon)
}

@BindingAdapter("ratioItem")
fun setIconRes(tvTitle: TextView, ratio: Ratio) {
    tvTitle.setText(ratio.title)
    if (ratio.isSelected) tvTitle.setTextColor(ContextCompat.getColor(tvTitle.context, R.color.active_blue))
    else tvTitle.setTextColor(Color.WHITE)
}

@BindingAdapter("filterItem")
fun setIconRes(imv: ImageView, filter: Filter) {
    imv.setImageResource(filter.icon)

}

@BindingAdapter("filterItem")
fun setIconRes(tvTitle: TextView, filter: Filter) {
    tvTitle.setText(filter.title)
    tvTitle.setTextColor(ContextCompat.getColor(tvTitle.context, R.color.white))
}

@BindingAdapter("adjustItem")
fun setAdjustIcon(imv: ImageView, adjustment: Adjustment) {
    imv.setImageResource(adjustment.icon)
    if (adjustment.isSelected) {

        imv.setImageResource(adjustment.activeIcon)
    } else {

        imv.setImageResource(adjustment.icon)
    }


}

@BindingAdapter("adjustItem")
fun setAdjustTitle(tvTitle: TextView, adjustment: Adjustment) {
    tvTitle.setText(adjustment.title)
    if (adjustment.isSelected) {
        tvTitle.setTextColor(ContextCompat.getColor(tvTitle.context, R.color.active_blue))
    } else {
        tvTitle.setTextColor(ContextCompat.getColor(tvTitle.context, R.color.white))
    }
}

@BindingAdapter("viewPager")
fun setUpViewPager(tabLayout: TabLayout, viewPager: ViewPager) {
    tabLayout.setupWithViewPager(viewPager)
}

@BindingAdapter("filterColorItem")
fun setFilterColorItem(imv: ImageView, filter: FilterColor) {
    imv.setImageResource(filter.icon)

}

@BindingAdapter("filterColorItem")
fun setFilterColorItem(tvTitle: TextView, filter: FilterColor) {
    tvTitle.text = filter.title
    tvTitle.setTextColor(ContextCompat.getColor(tvTitle.context, R.color.white))
}

@BindingAdapter("blendItem")
fun setBlendItem(tv: TextView, blend: Blend) {
    tv.setText(blend.title)
    if (blend.selected) {
        tv.setTextColor(ContextCompat.getColor(tv.context, R.color.active_blue))
    } else {
        tv.setTextColor(ContextCompat.getColor(tv.context, R.color.white))
    }
}

@BindingAdapter("blendItem")
fun setBlendItem(view: RelativeLayout, blend: Blend) {
    if (blend.selected) {
        view.setBackgroundResource(R.drawable.img_blend_selector)
    } else {
        view.setBackgroundResource(R.drawable.bg_img_blend_deselector)
    }
}


@BindingAdapter(value = ["blendItem", "path", "blendPath"])
fun setBlendItem(imv: ImageView, blendItem: Blend, path: String, blendPath: String) {
    val title = imv.context.resources.getString(blendItem.title)



    if (!blendPath.isEmpty()) {
        blendItem.loadBitmapWithFilterApplied(imv, path, blendPath, blendItem.filter!!)
    } else {
        imv.loadImage(path).apply(
            RequestOptions().transform(
                CenterCrop(),
                RoundedCorners(Util.convertDpToPixel(5f, imv.context).toInt())
            )
        ).into(imv)
    }


}

@BindingAdapter("fontItem")
fun setFontItem(tv: TextView, font: Font) {
    val am = tv.context.applicationContext.assets
    val typeface = Typeface.createFromAsset(am, font.fileName + ".ttf")
    tv.text = font.fileName
    tv.typeface = typeface
}

@BindingAdapter("colorItem")
fun setColorItem(button: Button, color: ColorText) {
    val resID = button.context.resources.getIdentifier(
        "color"+color.color,
        "color", button.context.packageName
    )
    button.setBackgroundColor(ContextCompat.getColor(button.context, resID))
}