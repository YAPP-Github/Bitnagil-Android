package com.threegap.bitnagil.presentation.setting.model.mvi

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import com.threegap.bitnagil.presentation.setting.model.VersionStateUiModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class SettingState(
    val useServiceAlarm: Boolean,
    val usePushAlarm: Boolean,
    val version: String,
    val versionState: VersionStateUiModel,
    val loading: Boolean,
    val logoutConfirmDialogVisible: Boolean,
) : MviState {
    companion object {
        val Init = SettingState(
            useServiceAlarm = false,
            usePushAlarm = false,
            version = "",
            versionState = VersionStateUiModel.NONE,
            loading = false,
            logoutConfirmDialogVisible = false,
        )
    }
}
