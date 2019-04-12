package com.example.core

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.annotation.LayoutRes
import android.view.View
import android.view.ViewGroup
import android.view.Window


class BaseDialog : Dialog {
    constructor(context: Context) : this(context, 0)
    constructor(context: Context, theme: Int) : super(context, theme) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(0x00000000)
        window!!.setBackgroundDrawable(gradientDrawable)
    }


    fun contentView(@LayoutRes resId: Int): BaseDialog {
        window!!.setContentView(resId)
        return this
    }

    fun contentView(view: View): BaseDialog {
        window!!.setContentView(view)
        return this
    }

    fun contentView(view: View, params: ViewGroup.LayoutParams): BaseDialog {
        window!!.setContentView(view, params)
        return this
    }

    fun layout(params: ViewGroup.LayoutParams): BaseDialog {
        window!!.setLayout(params.width, params.height)
        return this
    }

    fun cancelTouchOutside(cancel: Boolean): BaseDialog {
        setCanceledOnTouchOutside(cancel)
        return this
    }

    fun gravity(gravity: Int): BaseDialog {
        window!!.setGravity(gravity)
        return this
    }

    fun offset(x: Int, y: Int): BaseDialog {
        val layoutParams = window!!.attributes
        layoutParams.x = x
        layoutParams.y = y
        return this
    }

    fun anim(animType: AnimType): BaseDialog {
        when (animType.value) {
            0 -> window!!.setWindowAnimations(R.style.dialog_zoom)
            1 -> window!!.setWindowAnimations(R.style.dialog_anim_left)
            2 -> window!!.setWindowAnimations(R.style.dialog_anim_top)
            3 -> window!!.setWindowAnimations(R.style.dialog_anim_right)
            4 -> window!!.setWindowAnimations(R.style.dialog_anim_bottom)
        }
        return this
    }

    enum class AnimType(val value: Int) {
        DEFAULT(0),
        CENTER(0),
        LEFT(1),
        TOP(2),
        RIGHT(3),
        BOTTOM(4)

    }
}