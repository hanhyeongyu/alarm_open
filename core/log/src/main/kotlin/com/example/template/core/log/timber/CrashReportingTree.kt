package com.example.template.core.log.timber

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class CrashReportingTree() : Timber.Tree() {

    private val crashlytics = FirebaseCrashlytics.getInstance()

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if(priority == Log.VERBOSE || priority == Log.DEBUG){
            return
        }

        if(t != null){
            if(priority == Log.ERROR){
                crashlytics.recordException(Throwable(t))
            }
        }
    }

}