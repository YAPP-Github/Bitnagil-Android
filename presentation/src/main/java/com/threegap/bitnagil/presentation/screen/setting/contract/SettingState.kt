package com.threegap.bitnagil.presentation.screen.setting.contract

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SettingState(
    val useServiceAlarm: Boolean,
    val usePushAlarm: Boolean,
    val version: String,
    val loading: Boolean,
    val logoutConfirmDialogVisible: Boolean,
) : Parcelable {
    companion object {
        val Init = SettingState(
            useServiceAlarm = false,
            usePushAlarm = false,
            version = "",
            loading = false,
            logoutConfirmDialogVisible = false,
        )
    }
}
