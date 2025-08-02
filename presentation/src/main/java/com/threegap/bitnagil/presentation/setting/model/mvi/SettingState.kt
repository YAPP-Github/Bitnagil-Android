package com.threegap.bitnagil.presentation.setting.model.mvi

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import com.threegap.bitnagil.presentation.setting.model.ConfirmDialogType
import kotlinx.parcelize.Parcelize

@Parcelize
data class SettingState(
    val useServiceAlarm: Boolean,
    val usePushAlarm: Boolean,
    val version: String,
    val latestVersion: String,
    val loading: Boolean,
    val showConfirmDialog: ConfirmDialogType?,
) : MviState {
    companion object {
        val Init = SettingState(
            useServiceAlarm = false,
            usePushAlarm = false,
            version = "",
            latestVersion = "",
            loading = false,
            showConfirmDialog = null,
        )
    }
}
