package com.example.core.databases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.core.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.sql.SQLException



class DataBaseAssetHelper(val context: Context,val databaseName: String){
    fun copyDatabase() {
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