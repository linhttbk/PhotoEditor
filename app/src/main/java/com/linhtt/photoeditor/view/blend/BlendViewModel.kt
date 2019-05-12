package com.linhtt.photoeditor.view.blend

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.example.core.BaseViewModel
import com.example.core.ext.rx.AndroidSchedulerProvider
import com.example.core.ext.rx.fromComputation
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.adapter.BlendAdapter
import com.linhtt.photoeditor.adapter.FolderAdapter
import com.linhtt.photoeditor.adapter.PhotoAdapter
import com.linhtt.photoeditor.data.model.Blend
import com.linhtt.photoeditor.data.model.Photo
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import jp.co.cyberagent.android.gpuimage.filter.*

class BlendViewModel(var path: String, var imagePath: String) : BaseViewModel() {
    var adapter: BlendAdapter = BlendAdapter(initBlendItems(),path, imagePath)
    var photoAdapter = PhotoAdapter(ArrayList())
    val folderAdapter = FolderAdapter(java.util.ArrayList())
    val compositeDisposable = CompositeDisposable()
    val positionSelected = MutableLiveData<String>()

    init {

    }

    private fun initBlendItems(): ArrayList<Blend> {
        val results = ArrayList<Blend>()
        results.add(Blend())
        results.add(Blend(R.string.blend_difference_lbl, GPUImageDifferenceBlendFilter(), 50, true))
        results.add(Blend(R.string.blend_source_over_lbl, GPUImageSourceOverBlendFilter(), 50))
        results.add(Blend(R.string.blend_color_burn_lbl, GPUImageColorBurnBlendFilter(), 50))
        results.add(Blend(R.string.blend_color_dodge_lbl, GPUImageColorDodgeBlendFilter(), 50))
        results.add(Blend(R.string.blend_darken_lbl, GPUImageDarkenBlendFilter(), 50))
        results.add(Blend(R.string.blend_normal_lbl, GPUImageDissolveBlendFilter(), 50))
        results.add(Blend(R.string.blend_exclusion_lbl, GPUImageExclusionBlendFilter(), 50))
        return results
    }

    fun changePathBlend(path: String) {
        imagePath = path
        adapter.replace(path)
    }

    override fun onCleared() {
        super.onCleared()
        adapter.clear()
        compositeDisposable.dispose()
    }

    fun loadPhotoAndFolder(context: Context) {
        val disposable = Observable.just(loadAllPhotos(context))
            .map {
                val results: ArrayList<Photo> = ArrayList()
                it.forEach { items ->
                    if (!items.value.isEmpty()) {
                        val photo = Photo(items.key, items.value)
                        results.add(photo)
                    }

                }

                return@map results
            }.fromComputation(AndroidSchedulerProvider())
            .subscribe({ results ->
                folderAdapter.replace(results)
                if (adapter.itemCount > 0) {
                    positionSelected.value = folderAdapter.getItem(0).folder
                    photoAdapter.replace(folderAdapter.getItem(0).path)
                }
            }, {
                it.printStackTrace()

            })
        compositeDisposable.add(disposable)

    }


    private fun loadAllPhotos(context: Context): MutableMap<String, ArrayList<String>> {
        val items = mutableMapOf<String, ArrayList<String>>()
        val cursor: Cursor?
        val columnData: Int
        val columnFolderName: Int
        val uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val orderBy = MediaStore.Images.Media.BUCKET_DISPLAY_NAME;
        cursor = context.contentResolver.query(uri, projection, null, null, "$orderBy ASC")
        columnData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
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
        cursor.close()
        return items
    }
}