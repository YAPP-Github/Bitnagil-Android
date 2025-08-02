package com.threegap.bitnagil.data.auth.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TermsAgreementRequestDto(
    @SerialName("agreedToTermsOfService")
    val agreedToTermsOfService: Boolean,
    @SerialName("agreedToPrivacyPolicy")
    val agreedToPrivacyPolicy: Boolean,
    @SerialName("isOverFourteen")
    val isOverFourteen: Boolean,
)
