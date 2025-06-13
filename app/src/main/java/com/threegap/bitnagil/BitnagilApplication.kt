package com.threegap.bitnagil

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BitnagilApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
