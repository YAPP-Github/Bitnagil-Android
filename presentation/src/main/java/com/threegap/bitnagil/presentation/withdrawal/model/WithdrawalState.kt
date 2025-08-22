package com.threegap.bitnagil.presentation.withdrawal.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import kotlinx.parcelize.Parcelize

@Parcelize
data class WithdrawalState(
    val isLoading: Boolean = false,
    val isTermsChecked: Boolean = false,
    val selectedReason: WithdrawalReason? = null,
    val customReasonText: String = "",
    val showSuccessDialog: Boolean = false,
) : MviState {
    val isWithdrawalEnabled: Boolean
        get() = isTermsChecked && (selectedReason != null || customReasonText.isNotBlank())

    val finalWithdrawalReason: String
        get() = selectedReason?.displayText ?: customReasonText
}
