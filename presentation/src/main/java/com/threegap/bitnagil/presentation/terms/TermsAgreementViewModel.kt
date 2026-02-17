package com.threegap.bitnagil.presentation.terms

import android.util.Log
import androidx.lifecycle.ViewModel
import com.threegap.bitnagil.domain.auth.model.TermsAgreement
import com.threegap.bitnagil.domain.auth.usecase.SubmitTermsAgreementUseCase
import com.threegap.bitnagil.presentation.terms.contract.TermsAgreementSideEffect
import com.threegap.bitnagil.presentation.terms.contract.TermsAgreementState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class TermsAgreementViewModel @Inject constructor(
    private val submitTermsAgreementUseCase: SubmitTermsAgreementUseCase,
) : ContainerHost<TermsAgreementState, TermsAgreementSideEffect>, ViewModel() {

    override val container: Container<TermsAgreementState, TermsAgreementSideEffect> = container(initialState = TermsAgreementState.INIT)

    fun updateAllAgreements(agreed: Boolean) {
        intent {
            if (state.isLoading) return@intent
            reduce {
                state.copy(
                    agreedTermsOfService = agreed,
                    agreedPrivacyPolicy = agreed,
                    agreedOverFourteen = agreed,
                )
            }
        }
    }

    fun updateTermsOfService() {
        intent {
            if (state.isLoading) return@intent
            reduce { state.copy(agreedTermsOfService = !state.agreedTermsOfService) }
        }
    }

    fun updatePrivacyPolicy() {
        intent {
            if (state.isLoading) return@intent
            reduce { state.copy(agreedPrivacyPolicy = !state.agreedPrivacyPolicy) }
        }
    }

    fun updateOverFourteen() {
        intent {
            if (state.isLoading) return@intent
            reduce { state.copy(agreedOverFourteen = !state.agreedOverFourteen) }
        }
    }

    fun submitTermsAgreement() {
        intent {
            reduce { state.copy(isLoading = true) }
            val agreement = TermsAgreement(
                agreedToTermsOfService = state.agreedTermsOfService,
                agreedToPrivacyPolicy = state.agreedPrivacyPolicy,
                isOverFourteen = state.agreedOverFourteen,
            )
            submitTermsAgreementUseCase(agreement).fold(
                onSuccess = {
                    reduce { state.copy(isLoading = false) }
                    postSideEffect(TermsAgreementSideEffect.NavigateToOnBoarding)
                },
                onFailure = { error ->
                    Log.e("TermsAgreement", "Submit failed: ${error.message}")
                    reduce { state.copy(isLoading = false) }
                },
            )
        }
    }

    fun navigateToTermsOfService() {
        intent {
            postSideEffect(TermsAgreementSideEffect.NavigateToTermsOfService)
        }
    }

    fun navigateToPrivacyPolicy() {
        intent {
            postSideEffect(TermsAgreementSideEffect.NavigateToPrivacyPolicy)
        }
    }

    fun navigateToBack() {
        intent {
            postSideEffect(TermsAgreementSideEffect.NavigateToBack)
        }
    }
}
