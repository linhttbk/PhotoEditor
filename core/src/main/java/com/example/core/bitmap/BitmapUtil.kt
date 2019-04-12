package com.example.core.bitmap

import android.graphics.*
import android.media.ExifInterface
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLContext


class BitmapUtil {
    companion object {
        private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
            // Raw height and width of image
            val (height: Int, width: Int) = options.run { outHeight to outWidth }
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {

                val halfHeight: Int = height / 2
                val halfWidth: Int = width / 2

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                    inSampleSize *= 2
                }
            }

            return inSampleSize
        }

        fun decodeSampledBitmapFromResource(filePath: String, reqWidth: Int, reqHeight: Int): Bitmap {
            // First decode with inJustDecodeBounds=true to check dimensions
            return BitmapFactory.Options().run {
                inJustDecodeBounds = true
                BitmapFactory.decodeFile(filePath, this)

                // Calculate inSampleSize
                inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)

                // Decode bitmap with inSampleSize set
                inJustDecodeBounds = false

                BitmapFactory.decodeFile(filePath, this)
            }
        }

        fun rotateBitmapByExif(bitmap: Bitmap, exifInterface: ExifInterface): Bitmap {
            val degrees: Int
            val orientation =
                exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degrees = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degrees = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degrees = 270
                else -> degrees = 0
            }
            return bitmap
        }

        fun scaleBitmap(bitmap: Bitmap, reqWidth: Int, reqHeight: Int): Bitmap {
            val background = Bitmap.createBitmap(reqWidth, reqHeight, Bitmap.Config.ARGB_8888)

            val originalWidth = bitmap.width.toFloat()
            val originalHeight = bitmap.height.toFloat()

            val canvas = Canvas(background)

            val scale = reqWidth / originalWidth

            val xTranslation = 0.0f
            val yTranslation = (reqHeight - originalHeight * scale) / 2.0f

            val transformation = Matrix()
            transformation.postTranslate(xTranslation, yTranslation)
            transformation.preScale(scale, scale)

            val paint = Paint()
            paint.isFilterBitmap = true

            canvas.drawBitmap(bitmap, transformation, paint)

            return background
        }

        fun getScaledDownBitmap(bitmap: Bitmap, threshold: Int, isNecessaryToKeepOrig: Boolean): Bitmap {
            val width = bitmap.width
            val height = bitmap.height
            var newWidth = width
            var newHeight = height

            if (width > height && width > threshold) {
                newWidth = threshold
                newHeight = (height * newWidth.toFloat() / width).toInt()
            }

            if (width > height && width <= threshold) {
                //the bitmap is already smaller than our required dimension, no need to resize it
                return bitmap
            }

            if (width < height && height > threshold) {
                newHeight = threshold
                newWidth = (width * newHeight.toFloat() / height).toInt()
            }

            if (width < height && height <= threshold) {
                //the bitmap is already smaller than our required dimension, no need to resize it
                return bitmap
            }

            if (width == height && width > threshold) {
                newWidth = threshold
                newHeight = newWidth
            }

            return if (width == height && width <= threshold) {
                //the bitmap is already smaller than our required dimension, no need to resize it
                bitmap
            } else getResizedBitmap(bitmap, newWidth, newHeight, isNecessaryToKeepOrig)

        }

        private fun getResizedBitmap(
            bm: Bitmap,
            newWidth: Int,
            newHeight: Int,
            isNecessaryToKeepOrig: Boolean
        ): Bitmap {
            val width = bm.width
            val height = bm.height
            val scaleWidth = newWidth.toFloat() / width
            val scaleHeight = newHeight.toFloat() / height
            // CREATE A MATRIX FOR THE MANIPULATION
            val matrix = Matrix()
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight)

            // "RECREATE" THE NEW BITMAP
            val resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false)
            if (!isNecessaryToKeepOrig) {
                bm.recycle()
            }
            return resizedBitmap
        }

        fun getMaxTextureSize(): Int {
            // Safe minimum default size
            val IMAGE_MAX_BITMAP_DIMENSION = 2048

            // Get EGL Display
            val egl = EGLContext.getEGL() as EGL10
            val display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY)

            // Initialise
            val version = IntArray(2)
            egl.eglInitialize(display, version)

            // Query total number of configurations
            val totalConfigurations = IntArray(1)
            egl.eglGetConfigs(display, null, 0, totalConfigurations)

            // Query actual list configurations
            val configurationsList = arrayOfNulls<EGLConfig>(totalConfigurations[0])
            egl.eglGetConfigs(display, configurationsList, totalConfigurations[0], totalConfigurations)

            val textureSize = IntArray(1)
            var maximumTextureSize = 0

            // Iterate through all the configurations to located the maximum texture size
            for (i in 0 until totalConfigurations[0]) {
                // Only need to check for width since opengl textures are always squared
                egl.eglGetConfigAttrib(display, configurationsList[i], EGL10.EGL_MAX_PBUFFER_WIDTH, textureSize)

                // Keep track of the maximum texture size
                if (maximumTextureSize < textureSize[0])
                    maximumTextureSize = textureSize[0]
            }

            // Release
            egl.eglTerminate(display)

            // Return largest texture size found, or default
            return Math.max(maximumTextureSize, IMAGE_MAX_BITMAP_DIMENSION)
        }

    }


}