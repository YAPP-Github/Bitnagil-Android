package com.threegap.bitnagil.presentation.terms.model

sealed interface TermsAgreementSideEffect {
    data object NavigateToTermsOfService : TermsAgreementSideEffect
    data object NavigateToPrivacyPolicy : TermsAgreementSideEffect
    data object NavigateToOnBoarding : TermsAgreementSideEffect
    data object NavigateToBack : TermsAgreementSideEffect
}
