package com.linhtt.photoeditor.adapter

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.Color
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.data.model.Filter
import com.linhtt.photoeditor.data.model.Ratio
import com.linhtt.photoeditor.view.editor.EditorViewModel
import com.linhtt.photoeditor.view.home.HomeViewModel
import jp.co.cyberagent.android.gpuimage.GPUImageView
import java.io.File

@BindingAdapter("imgPath")
fun setImagePath(imv: ImageView, path: String) {
    Glide.with(imv.context).load(path).thumbnail(0.5f).into(imv)

}

@BindingAdapter("gpuPath")
fun setGpuPath(gpuImageView: GPUImageView, path: String) {
    Log.e("BindingAdapter", path)
    gpuImageView.setImage(File(path))
}

@BindingAdapter(value = ["viewModel", "shareModel"])
fun setData(gpuImageView: GPUImageView, viewModel: EditorViewModel, shareModel: HomeViewModel) {
    val filePath = shareModel.loadBitmapFromCache()
    if (filePath != null) {
        gpuImageView.setImage(File(filePath))
    } else {
        gpuImageView.setImage(File(viewModel.path))
    }

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

@BindingAdapter("viewPager")
fun setUpViewPager(tabLayout: TabLayout, viewPager: ViewPager) {
    tabLayout.setupWithViewPager(viewPager)
}