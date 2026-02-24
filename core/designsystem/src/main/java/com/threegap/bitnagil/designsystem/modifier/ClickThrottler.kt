package com.threegap.bitnagil.designsystem.modifier

import android.os.SystemClock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

class ClickThrottler(private val throttleTimeMs: Long = 500L) {
    private var lastClickTime: Long = 0L

    fun processEvent(event: () -> Unit) {
        val now = SystemClock.elapsedRealtime()
        if (now - lastClickTime >= throttleTimeMs) {
            lastClickTime = now
            event()
        }
    }
}

@Composable
fun throttled(
    throttleTimeMs: Long = 500L,
    onClick: () -> Unit
): () -> Unit {
    val throttler = remember { ClickThrottler(throttleTimeMs) }

    return remember(onClick) {
        { throttler.processEvent(onClick) }
    }
}
