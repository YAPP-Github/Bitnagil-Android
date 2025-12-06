package com.threegap.bitnagil.presentation.setting.model.mvi

sealed class SettingSideEffect {
    data object NavigateToLogin : SettingSideEffect()
    data object NavigateToWithdrawal : SettingSideEffect()
}
