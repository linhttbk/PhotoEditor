package com.example.core.ext.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.example.core.BaseActivity
import com.example.core.BaseFragment

fun ImageView.loadImage( url: String, placeHolder: Int): RequestBuilder<Drawable> {
    return Glide.with(context).load(url).placeholder(placeHolder)
}
fun ImageView.loadImage( url: String): RequestBuilder<Drawable> {
    return Glide.with(context).load(url)
}

fun ImageView.loadImage(bitmap: Bitmap, placeHolder: Int): RequestBuilder<Drawable> {
    return Glide.with(context).load(bitmap).placeholder(placeHolder)
}

fun ImageView.loadImage( bitmap: Bitmap): RequestBuilder<Drawable> {
    return Glide.with(context).load(bitmap)
}
fun BaseActivity<*, *>.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun BaseFragment<*, *>.showToast(msg: String) {
    Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
}

fun View.slideUp() {
    this.visibility = View.VISIBLE
    val animate = TranslateAnimation(
        0f, // fromXDelta
        0f, // toXDelta
        this.height.toFloat(), // fromYDelta
        0f
    )                // toYDelta
    animate.duration = 500
    this.startAnimation(animate)
}

fun View.slideDown() {
    this.visibility = View.INVISIBLE
    val animate = TranslateAnimation(
        0f, // fromXDelta
        0f, // toXDelta
        0f, // fromYDelta
        this.height.toFloat()
    ) // toYDelta             // toYDelta
    animate.duration = 500
    this.startAnimation(animate)
}


