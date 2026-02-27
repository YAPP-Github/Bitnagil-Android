package com.threegap.bitnagil.presentation.screen.terms.contract

data class TermsAgreementState(
    val isLoading: Boolean,
    val agreedTermsOfService: Boolean,
    val agreedPrivacyPolicy: Boolean,
    val agreedOverFourteen: Boolean,
) {
    val isAllAgreed: Boolean
        get() = agreedTermsOfService && agreedPrivacyPolicy && agreedOverFourteen

    val submitEnabled: Boolean
        get() = !isLoading && isAllAgreed

    companion object {
        val INIT = TermsAgreementState(
            isLoading = false,
            agreedTermsOfService = false,
            agreedPrivacyPolicy = false,
            agreedOverFourteen = false,
        )
    }
}
