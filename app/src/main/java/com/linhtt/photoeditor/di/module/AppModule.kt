package com.linhtt.photoeditor.di.module

import android.support.v4.app.FragmentManager
import com.linhtt.photoeditor.view.crop.CropViewModel
import com.linhtt.photoeditor.view.editor.EditorViewModel
import com.linhtt.photoeditor.view.home.HomeViewModel
import com.linhtt.photoeditor.view.photo.PhotoViewModel
import com.linhtt.photoeditor.view.sticker.StickerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val app_module = module { }
val view_model_module = module {
    single { HomeViewModel() }
    single { PhotoViewModel(androidContext()) }
    factory { (path: String, fragmentManager: FragmentManager) -> EditorViewModel(path, fragmentManager) }
    factory { (path: String) -> CropViewModel(path) }
    factory {(position:Int)-> StickerViewModel(position) }
}
