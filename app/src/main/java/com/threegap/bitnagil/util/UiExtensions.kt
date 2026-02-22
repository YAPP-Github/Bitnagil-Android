package com.threegap.bitnagil.util

import android.app.Activity
import androidx.core.view.WindowCompat

fun Activity.setStatusBarContentColor(isLightContent: Boolean) {
    val window = this.window
    val view = window.decorView
    val wic = WindowCompat.getInsetsController(window, view)

    wic.isAppearanceLightStatusBars = !isLightContent
}
