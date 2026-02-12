package com.threegap.bitnagil.presentation.setting.contract

sealed interface SettingSideEffect {
    data object NavigateToLogin : SettingSideEffect
    data object NavigateToWithdrawal : SettingSideEffect
}
