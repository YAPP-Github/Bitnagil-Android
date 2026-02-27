package com.threegap.bitnagil.presentation.screen.withdrawal.contract

sealed interface WithdrawalSideEffect {
    data object NavigateToBack : WithdrawalSideEffect
    data object NavigateToLogin : WithdrawalSideEffect
}
