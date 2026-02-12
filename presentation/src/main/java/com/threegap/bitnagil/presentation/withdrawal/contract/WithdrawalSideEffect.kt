package com.threegap.bitnagil.presentation.withdrawal.contract

sealed interface WithdrawalSideEffect {
    data object NavigateToBack : WithdrawalSideEffect
    data object NavigateToLogin : WithdrawalSideEffect
}
