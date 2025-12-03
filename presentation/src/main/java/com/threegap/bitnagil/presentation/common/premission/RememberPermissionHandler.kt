package com.threegap.bitnagil.presentation.common.premission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.threegap.bitnagil.designsystem.component.block.BitnagilAlertDialog

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun rememberPermissionHandler(
    permission: String,
    dialogDescription: String,
    onGranted: () -> Unit,
): PermissionHandler {
    val context = LocalContext.current
    var showSettingsDialog by remember { mutableStateOf(false) }
    var permissionRequestCount by rememberSaveable { mutableIntStateOf(0) }

    val permissionState = rememberPermissionState(
        permission = permission,
        onPermissionResult = { isGranted ->
            if (isGranted) {
                permissionRequestCount = 0
                onGranted()
            } else {
                permissionRequestCount++
            }
        },
    )

    val handler = remember(context) {
        object : PermissionHandler {
            override val isGranted: Boolean
                get() = permissionState.status.isGranted

            override fun requestPermission() {
                when {
                    permissionState.status.isGranted -> {
                        onGranted()
                    }

                    permissionState.status.shouldShowRationale -> {
                        permissionState.launchPermissionRequest()
                    }

                    else -> {
                        if (permissionRequestCount == 0) {
                            permissionState.launchPermissionRequest()
                        } else {
                            showSettingsDialog = true
                        }
                    }
                }
            }

            @Composable
            override fun PermissionDialogs() {
                if (showSettingsDialog) {
                    BitnagilAlertDialog(
                        title = "권한 설정이 필요합니다.",
                        description = dialogDescription,
                        dismissButtonText = "닫기",
                        confirmButtonText = "설정으로 이동",
                        onConfirm = {
                            showSettingsDialog = false
                            val intent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", context.packageName, null),
                            )
                            context.startActivity(intent)
                        },
                        onDismiss = { showSettingsDialog = false },
                    )
                }
            }
        }
    }

    return handler
}
