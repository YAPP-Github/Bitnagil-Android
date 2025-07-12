package com.threegap.bitnagil.domain.auth.model

data class TermsAgreement(
    val agreedToTermsOfService: Boolean,
    val agreedToPrivacyPolicy: Boolean,
    val isOverFourteen: Boolean,
) {
    val isValid: Boolean
        get() = agreedToTermsOfService && agreedToPrivacyPolicy && isOverFourteen
}
