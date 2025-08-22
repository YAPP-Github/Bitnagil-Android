package com.threegap.bitnagil.presentation.withdrawal.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent

sealed class WithdrawalIntent : MviIntent {
    data object OnTermsToggle : WithdrawalIntent()
    data object OnBackClick : WithdrawalIntent()
    data object ShowSuccessDialog : WithdrawalIntent()
    data object OnSuccessDialogConfirm : WithdrawalIntent()
    data class UpdateLoading(val isLoading: Boolean) : WithdrawalIntent()
    data class OnReasonSelected(val reason: WithdrawalReason?) : WithdrawalIntent()
    data class OnCustomReasonChanged(val text: String) : WithdrawalIntent()
}
