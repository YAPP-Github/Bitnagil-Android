package com.threegap.bitnagil.presentation.terms.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import kotlinx.parcelize.Parcelize

@Parcelize
data class TermsAgreementState(
    val isLoading: Boolean = false,
    val agreedTermsOfService: Boolean = false,
    val agreedPrivacyPolicy: Boolean = false,
    val agreedOverFourteen: Boolean = false,
) : MviState {
    val isAllAgreed: Boolean
        get() = agreedTermsOfService && agreedPrivacyPolicy && agreedOverFourteen

    val submitEnabled: Boolean
        get() = !isLoading && isAllAgreed
}
