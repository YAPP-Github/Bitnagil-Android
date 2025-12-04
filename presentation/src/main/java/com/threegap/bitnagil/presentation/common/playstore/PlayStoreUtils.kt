package com.threegap.bitnagil.presentation.common.playstore

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.threegap.bitnagil.presentation.BuildConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.system.exitProcess

private const val PACKAGE_NAME = BuildConfig.APPLICATION_ID
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

@Composable
fun updateAvailable(): UpdateAvailableState {
    val context = LocalContext.current
    var isUpdateAvailable by remember { mutableStateOf(UpdateAvailableState.NONE) }

    LaunchedEffect(Unit) {
        isUpdateAvailable = context.checkForUpdateAvailability()
    }

    return isUpdateAvailable
}

private suspend fun Context.checkForUpdateAvailability(): UpdateAvailableState = suspendCancellableCoroutine { continuation ->
    val appUpdateManager = AppUpdateManagerFactory.create(this)
    val appUpdateInfoTask = appUpdateManager.appUpdateInfo

    appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
        if (!continuation.isActive) return@addOnSuccessListener

        val isAvailable = appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE

        val isAllowed = appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) ||
            appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)

        val state = if (isAvailable && isAllowed) UpdateAvailableState.NEED_UPDATE else UpdateAvailableState.LATEST
        continuation.resume(state)
    }

    appUpdateInfoTask.addOnFailureListener {
        if (continuation.isActive) {
            continuation.resume(UpdateAvailableState.NONE)
        }
    }
}
