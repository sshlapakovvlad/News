package com.sshlapakovvlad.news

import android.app.Application
import timber.log.Timber

class NewsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
