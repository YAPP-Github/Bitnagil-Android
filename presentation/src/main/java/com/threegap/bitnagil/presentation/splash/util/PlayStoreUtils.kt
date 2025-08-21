package com.threegap.bitnagil.presentation.splash.util

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

private const val PACKAGE_NAME = "com.threegap.bitnagil"
private const val GOOGLE_PLAY_PACKAGE = "com.android.vending"
private const val APP_EXIT_DELAY = 500L

fun openAppInPlayStore(
    activity: ComponentActivity?,
    shouldFinishApp: Boolean = true,
) {
    activity?.let {
        val isSuccess = tryOpenPlayStore(it) || tryOpenWebBrowser(it)

        if (isSuccess && shouldFinishApp) {
            finishAppWithDelay(it)
        }
    }
}

private fun tryOpenPlayStore(activity: ComponentActivity): Boolean =
    try {
        val intent = createPlayStoreIntent()
        activity.startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        false
    }

private fun tryOpenWebBrowser(activity: ComponentActivity): Boolean =
    try {
        val intent = createWebIntent()
        activity.startActivity(intent)
        true
    } catch (e: Exception) {
        false
    }

private fun createPlayStoreIntent(): Intent =
    Intent(
        Intent.ACTION_VIEW,
        "market://details?id=$PACKAGE_NAME".toUri(),
    ).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        setPackage(GOOGLE_PLAY_PACKAGE)
    }

private fun createWebIntent(): Intent =
    Intent(
        Intent.ACTION_VIEW,
        "https://play.google.com/store/apps/details?id=$PACKAGE_NAME".toUri(),
    ).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

private fun finishAppWithDelay(activity: ComponentActivity) {
    activity.lifecycleScope.launch {
        delay(APP_EXIT_DELAY)
        activity.finishAffinity()
        exitProcess(0)
    }
}
