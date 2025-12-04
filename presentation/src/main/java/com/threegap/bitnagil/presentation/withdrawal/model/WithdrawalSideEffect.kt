package com.threegap.bitnagil.presentation.withdrawal.model

sealed interface WithdrawalSideEffect {
    data object NavigateToBack : WithdrawalSideEffect
    data object NavigateToLogin : WithdrawalSideEffect
}
