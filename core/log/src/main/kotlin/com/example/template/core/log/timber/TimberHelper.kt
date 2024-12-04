package com.example.template.core.log.timber

import com.example.template.core.log.BuildConfig
import timber.log.Timber.DebugTree
import timber.log.Timber.Forest.plant


class TimberHelper {
    companion object{
        fun setupLogger(){
            if(BuildConfig.DEBUG){
                plant(DebugTree())
            }else{
                plant(CrashReportingTree())
            }
        }
    }
}

