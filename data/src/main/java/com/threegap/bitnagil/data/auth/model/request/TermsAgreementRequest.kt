package com.threegap.bitnagil.data.auth.model.request

import com.threegap.bitnagil.domain.auth.model.TermsAgreement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TermsAgreementRequest(
    @SerialName("agreedToTermsOfService")
    val agreedToTermsOfService: Boolean,
    @SerialName("agreedToPrivacyPolicy")
    val agreedToPrivacyPolicy: Boolean,
    @SerialName("isOverFourteen")
    val isOverFourteen: Boolean,
)

internal fun TermsAgreement.toDto() =
    TermsAgreementRequest(
        agreedToTermsOfService = this.agreedToTermsOfService,
        agreedToPrivacyPolicy = this.agreedToPrivacyPolicy,
        isOverFourteen = this.isOverFourteen,
    )
