package com.threegap.bitnagil.presentation.withdrawal.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviSideEffect

sealed interface WithdrawalSideEffect : MviSideEffect {
    data object NavigateToBack : WithdrawalSideEffect
    data object NavigateToLogin : WithdrawalSideEffect
}
