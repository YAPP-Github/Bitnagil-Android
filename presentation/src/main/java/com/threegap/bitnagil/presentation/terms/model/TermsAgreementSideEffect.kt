package com.threegap.bitnagil.presentation.terms.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviSideEffect

sealed interface TermsAgreementSideEffect : MviSideEffect {
    data object NavigateToTermsOfService : TermsAgreementSideEffect
    data object NavigateToPrivacyPolicy : TermsAgreementSideEffect
    data object NavigateToOnBoarding : TermsAgreementSideEffect
    data object NavigateToBack : TermsAgreementSideEffect
}
