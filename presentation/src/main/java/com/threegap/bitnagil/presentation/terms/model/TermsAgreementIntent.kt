package com.threegap.bitnagil.presentation.terms.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent

sealed class TermsAgreementIntent : MviIntent {
    data class SetLoading(val isLoading: Boolean) : TermsAgreementIntent()

    data class ToggleAllAgreements(val agreed: Boolean) : TermsAgreementIntent()

    data class ToggleTermsOfService(val agreed: Boolean) : TermsAgreementIntent()

    data class TogglePrivacyPolicy(val agreed: Boolean) : TermsAgreementIntent()

    data class ToggleOverFourteen(val agreed: Boolean) : TermsAgreementIntent()

    data object SubmitSuccess : TermsAgreementIntent()

    data object SubmitFailure : TermsAgreementIntent()

    data object BackButtonClick : TermsAgreementIntent()
}
