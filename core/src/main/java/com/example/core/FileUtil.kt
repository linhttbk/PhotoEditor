package com.example.core

import android.content.Context
import android.net.Uri
import android.os.Environment.getExternalStorageDirectory
import android.support.v4.content.FileProvider
import java.io.File


class FileUtil {
    companion object {
        fun createImageFile(imageName: String, folderName: String): File? {
            val photoDirectory = File(getExternalStorageDirectory(), folderName)
            if (!photoDirectory.exists() && !photoDirectory.mkdirs()) {
                return null
            }
            return File(photoDirectory, imageName)
        }

        fun getUriForFile(context: Context,authority:String , file: File): Uri {
            return FileProvider.getUriForFile(
                context,
                authority,
                file
            )
        }
    }
}