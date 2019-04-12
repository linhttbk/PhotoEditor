package com.linhtt.photoeditor.view.photo

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns
import android.util.Log
import android.view.View
import com.example.core.BaseRecycleViewAdapter
import com.example.core.BaseViewModel
import com.linhtt.photoeditor.adapter.FolderAdapter
import com.linhtt.photoeditor.adapter.PhotoAdapter
import com.linhtt.photoeditor.data.model.Photo
import java.util.*


class PhotoViewModel(context: Context) : BaseViewModel() {
    var items: ArrayList<Photo>
    val adapter = FolderAdapter(ArrayList())
    val photoAdapter = PhotoAdapter(ArrayList())
    val positionSelected = MutableLiveData<String>()
    val date  = Calendar.getInstance().time


    init {
        items = getAllImagePath(context)
        adapter.replace(items)
        if (adapter.itemCount > 0) {
            positionSelected.value = adapter.getItem(0).folder
            photoAdapter.replace(adapter.getItem(0).path)
        }

        adapter.setOnItemClickListener(object : BaseRecycleViewAdapter.ItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                positionSelected.value = adapter.getItem(position).folder
                photoAdapter.replace(adapter.getItem(position).path)
            }

        })
        Log.e("curent","$date.year")


    }

    private fun getAllImagePath(context: Context): ArrayList<Photo> {

        val results: ArrayList<Photo> = ArrayList()
        val items = mutableMapOf<String, ArrayList<String>>()
        val cursor: Cursor
        val columnData: Int
        val columnFolderName: Int
        val uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val orderBy = MediaStore.Images.Media.BUCKET_DISPLAY_NAME;
        cursor = context.contentResolver.query(uri, projection, null, null, orderBy + " ASC")
        columnData = cursor.getColumnIndexOrThrow(MediaColumns.DATA)
        columnFolderName = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        while (cursor.moveToNext()) {
            val absolutePathOfImage = cursor.getString(columnData)
            val folder = cursor.getString(columnFolderName)
            if (items.containsKey(folder)) {
                items[folder]!!.add(absolutePathOfImage)
            } else {
                items[folder] = arrayListOf(absolutePathOfImage)

            }


        }
        items.forEach {
            if (!it.value.isEmpty()) {
                val photo = Photo(it.key, it.value)
                results.add(photo)
            }

        }
        return results
    }
}