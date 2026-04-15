package com.threegap.bitnagil.presentation.screen.withdrawal.contract

import com.threegap.bitnagil.presentation.screen.withdrawal.model.WithdrawalReason

data class WithdrawalState(
    val isLoading: Boolean,
    val isTermsChecked: Boolean,
    val selectedReason: WithdrawalReason?,
    val customReasonText: String,
    val showSuccessDialog: Boolean,
) {
    val isWithdrawalEnabled: Boolean
        get() = isTermsChecked && (selectedReason != null || customReasonText.isNotBlank())

    val finalWithdrawalReason: String
        get() = selectedReason?.displayText ?: customReasonText

    companion object {
        val INIT = WithdrawalState(
            isLoading = false,
            isTermsChecked = false,
            selectedReason = null,
            customReasonText = "",
            showSuccessDialog = false,
        )
    }
}
