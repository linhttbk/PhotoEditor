package com.linhtt.photoeditor.util

import android.content.Context
import android.util.DisplayMetrics

class Util {
    companion object {
        fun convertDpToPixel(dp: Float, context: Context): Float {
            return dp * (context.resources.displayMetrics.densityDpi/ DisplayMetrics.DENSITY_DEFAULT)
        }
    }

}