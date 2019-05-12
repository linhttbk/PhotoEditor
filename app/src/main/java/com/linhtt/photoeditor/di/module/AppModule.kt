package com.linhtt.photoeditor.di.module

import android.net.Uri
import android.support.v4.app.FragmentManager
import com.linhtt.photoeditor.view.adjust.AdjustViewModel
import com.linhtt.photoeditor.view.blend.BlendViewModel
import com.linhtt.photoeditor.view.blur.BlurViewModel
import com.linhtt.photoeditor.view.crop.CropViewModel
import com.linhtt.photoeditor.view.draw.DrawViewModel
import com.linhtt.photoeditor.view.editor.EditorViewModel
import com.linhtt.photoeditor.view.filter.FilterViewModel
import com.linhtt.photoeditor.view.home.HomeViewModel
import com.linhtt.photoeditor.view.photo.PhotoViewModel
import com.linhtt.photoeditor.view.preview.PreviewViewModel
import com.linhtt.photoeditor.view.rotate.ConvolutionViewModel
import com.linhtt.photoeditor.view.rotate.ShadowViewModel
import com.linhtt.photoeditor.view.sticker.StickerPageViewModel
import com.linhtt.photoeditor.view.sticker.StickerViewModel
import com.linhtt.photoeditor.view.text.FontColorViewModel
import com.linhtt.photoeditor.view.text.TextStickViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val app_module = module { }
val view_model_module = module {
    single { HomeViewModel() }
    single { PhotoViewModel(androidContext()) }
    factory { (path: String, fragmentManager: FragmentManager) -> EditorViewModel(path, fragmentManager) }
    factory { (path: String) -> CropViewModel(path) }
    factory { (position: Int) -> StickerPageViewModel(position) }
    factory { (fragmentManager: FragmentManager) -> StickerViewModel(fragmentManager) }
    factory { (fragmentManager: FragmentManager) -> FontColorViewModel(fragmentManager) }
    factory { AdjustViewModel() }
    factory { FilterViewModel() }
    factory { BlurViewModel() }
    factory { ConvolutionViewModel() }
    factory { ShadowViewModel () }
    factory { (fragmentManager: FragmentManager) -> DrawViewModel(fragmentManager) }
    factory { (text: String) -> TextStickViewModel(text) }
    factory { (path: String) -> BlendViewModel(path, "") }
    factory { (uri: Uri) -> PreviewViewModel(uri) }
}
//"/storage/emulated/0/Pictures/B612/B612_20180922_154853_490.jpg"