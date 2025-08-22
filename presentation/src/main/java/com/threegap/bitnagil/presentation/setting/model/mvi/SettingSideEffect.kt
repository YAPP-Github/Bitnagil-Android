package com.threegap.bitnagil.presentation.setting.model.mvi

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviSideEffect

sealed class SettingSideEffect : MviSideEffect {
    data object NavigateToLogin : SettingSideEffect()
    data object NavigateToWithdrawal : SettingSideEffect()
}
