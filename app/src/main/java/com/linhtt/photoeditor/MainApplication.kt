package com.linhtt.photoeditor

import android.app.Application
import android.content.Context
import com.linhtt.photoeditor.di.module.app_module
import com.linhtt.photoeditor.di.module.view_model_module
import org.koin.android.ext.android.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(app_module, view_model_module))
    }
}