package com.threegap.bitnagil.domain.auth.model

data class TermsAgreement(
    val agreedToTermsOfService: Boolean,
    val agreedToPrivacyPolicy: Boolean,
    val isOverFourteen: Boolean,
) {
    fun isValid(): Boolean = agreedToTermsOfService && agreedToPrivacyPolicy && isOverFourteen
}
