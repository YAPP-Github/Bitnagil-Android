package com.threegap.bitnagil.presentation.setting.model.mvi

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent

sealed class SettingIntent : MviIntent {
    data class LoadSettingSuccess(
        val useServiceAlarm: Boolean,
        val usePushAlarm: Boolean,
        val version: String,
        val latestVersion: String,
    ) : SettingIntent()

    data object ShowLogoutConfirmDialog : SettingIntent()
    data object HideConfirmDialog : SettingIntent()
    data object ToggleServiceAlarm : SettingIntent()
    data object TogglePushAlarm : SettingIntent()
    data object LogoutLoading : SettingIntent()
    data object LogoutSuccess : SettingIntent()
    data object LogoutFailure : SettingIntent()
    data object OnWithdrawalClick : SettingIntent()
}
