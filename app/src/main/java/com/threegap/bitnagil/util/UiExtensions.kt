package com.threegap.bitnagil.util

import android.app.Activity
import androidx.core.view.WindowCompat

fun Activity.setStatusBarContentColor(isLightContent: Boolean) {
    val window = this.window
    val wic = WindowCompat.getInsetsController(window, window.decorView)

    val targetAppearance = !isLightContent

    if (wic.isAppearanceLightStatusBars != targetAppearance) {
        wic.isAppearanceLightStatusBars = targetAppearance
    }
}
