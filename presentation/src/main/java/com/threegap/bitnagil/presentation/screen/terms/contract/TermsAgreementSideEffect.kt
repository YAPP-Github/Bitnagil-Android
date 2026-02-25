package com.threegap.bitnagil.presentation.screen.terms.contract

sealed interface TermsAgreementSideEffect {
    data object NavigateToTermsOfService : TermsAgreementSideEffect
    data object NavigateToPrivacyPolicy : TermsAgreementSideEffect
    data object NavigateToOnBoarding : TermsAgreementSideEffect
    data object NavigateToBack : TermsAgreementSideEffect
}
