package com.threegap.bitnagil.presentation.screen.setting.contract

sealed interface SettingSideEffect {
    data object NavigateToLogin : SettingSideEffect
    data object NavigateToWithdrawal : SettingSideEffect
}
