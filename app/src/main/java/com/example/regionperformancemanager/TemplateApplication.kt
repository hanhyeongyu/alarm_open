package com.example.regionperformancemanager

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.example.template.core.log.timber.TimberHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class TemplateApplication: Application(), ImageLoaderFactory {

    @Inject
    lateinit var imageLoader: dagger.Lazy<ImageLoader>

    override fun onCreate() {
        super.onCreate()

        TimberHelper.setupLogger()
    }

    override fun newImageLoader(): ImageLoader = imageLoader.get()

}