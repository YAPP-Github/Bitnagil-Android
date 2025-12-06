package com.threegap.bitnagil.presentation.common.premission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Stable
interface PermissionHandler {
    val isGranted: Boolean

    fun requestPermission()

    @Composable
    fun PermissionDialogs()
}
