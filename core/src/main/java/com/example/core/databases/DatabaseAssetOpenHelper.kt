package com.example.core.databases

import android.content.Context
import java.io.FileOutputStream
import java.io.IOException


class DatabaseAssetOpenHelper {

    fun copyDatabase(context: Context, databaseName: String) {
        val dbPath = context.getDatabasePath(databaseName)
        if (dbPath.exists()) return
        dbPath.parentFile.mkdirs()
        try {
            val inputStream = context.assets.open("databases/"+databaseName)
            val output = FileOutputStream(dbPath)
            inputStream.copyTo(output)
            output.flush()
            output.close()
            inputStream.close()

        } catch (io: IOException) {
            io.printStackTrace()
        }
    }
}