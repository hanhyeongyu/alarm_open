package com.example.regionperformancemanager

import android.app.Application
import com.example.template.core.log.timber.TimberHelper
import dagger.hilt.android.HiltAndroidApp



@HiltAndroidApp
class TemplateApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        TimberHelper.setupLogger()
    }
}