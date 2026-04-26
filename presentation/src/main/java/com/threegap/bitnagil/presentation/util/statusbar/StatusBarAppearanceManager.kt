package com.threegap.bitnagil.presentation.util.statusbar

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.view.PixelCopy
import android.view.Window
import androidx.core.graphics.createBitmap
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.graphics.get
import androidx.core.view.WindowInsetsControllerCompat

class StatusBarAppearanceManager {
    fun applyStatusBarColorByLuminance(window: Window) {
        val height = getStatusBarHeight(window = window)
        if (height <= 0) return

        captureStatusBarBitmap(
            window = window,
            height = height,
        ) { bmp ->
            bmp?.let {
                val lum = calculateLuminance(it)
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = (lum > 150)
            }
        }
    }

    private fun getStatusBarHeight(window: Window): Int {
        val decorView = window.decorView
        val insets = ViewCompat.getRootWindowInsets(decorView)
        return insets?.getInsets(WindowInsetsCompat.Type.statusBars())?.top ?: 0
    }

    private fun captureStatusBarBitmap(
        window: Window,
        height: Int,
        onResult: (Bitmap?) -> Unit
    ) {
        val rect = Rect(0, 0, window.decorView.width, height)
        val bmp = createBitmap(rect.width(), rect.height())
        PixelCopy.request(window, rect, bmp, {_ -> onResult(bmp)}, Handler(Looper.getMainLooper()))
    }

    private fun calculateLuminance(bitmap: Bitmap): Int {
        val x = bitmap.width / 2
        val y = bitmap.height / 2
        val px = bitmap[x, y]
        return (0.299 * Color.red(px) + 0.587 * Color.green(px) + 0.114 * Color.blue(px)).toInt()
    }
}
