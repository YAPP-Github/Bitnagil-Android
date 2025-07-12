package com.threegap.bitnagil.presentation.terms

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.auth.model.TermsAgreement
import com.threegap.bitnagil.domain.auth.usecase.SubmitTermsAgreementUseCase
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.terms.model.TermsAgreementIntent
import com.threegap.bitnagil.presentation.terms.model.TermsAgreementSideEffect
import com.threegap.bitnagil.presentation.terms.model.TermsAgreementState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import javax.inject.Inject

@HiltViewModel
class TermsAgreementViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val submitTermsAgreementUseCase: SubmitTermsAgreementUseCase,
) : MviViewModel<TermsAgreementState, TermsAgreementSideEffect, TermsAgreementIntent>(
    initState = TermsAgreementState(),
    savedStateHandle = savedStateHandle,
) {
    override suspend fun SimpleSyntax<TermsAgreementState, TermsAgreementSideEffect>.reduceState(
        intent: TermsAgreementIntent,
        state: TermsAgreementState,
    ): TermsAgreementState? = when (intent) {
        is TermsAgreementIntent.SetLoading -> {
            state.copy(isLoading = intent.isLoading)
        }

        is TermsAgreementIntent.ToggleAllAgreements -> {
            if (state.isLoading) null
            else state.copy(
                agreedTermsOfService = intent.agreed,
                agreedPrivacyPolicy = intent.agreed,
                agreedOverFourteen = intent.agreed,
            )
        }

        is TermsAgreementIntent.ToggleTermsOfService -> {
            if (state.isLoading) null
            else state.copy(agreedTermsOfService = intent.agreed)
        }

        is TermsAgreementIntent.TogglePrivacyPolicy -> {
            if (state.isLoading) null
            else state.copy(agreedPrivacyPolicy = intent.agreed)
        }

        is TermsAgreementIntent.ToggleOverFourteen -> {
            if (state.isLoading) null
            else state.copy(agreedOverFourteen = intent.agreed)
        }

        is TermsAgreementIntent.SubmitSuccess -> {
            sendSideEffect(TermsAgreementSideEffect.NavigateToOnBoarding)
            state.copy(isLoading = false)
        }

        is TermsAgreementIntent.SubmitFailure -> {
            state.copy(isLoading = false)
        }

        is TermsAgreementIntent.BackButtonClick -> {
            sendSideEffect(TermsAgreementSideEffect.NavigateToBack)
            null
        }
    }

    fun submitTermsAgreement() {
        sendIntent(TermsAgreementIntent.SetLoading(true))
        viewModelScope.launch {
            val currentState = container.stateFlow.value
            val agreement = TermsAgreement(
                agreedToTermsOfService = currentState.agreedTermsOfService,
                agreedToPrivacyPolicy = currentState.agreedPrivacyPolicy,
                isOverFourteen = currentState.agreedOverFourteen,
            )

            submitTermsAgreementUseCase(agreement)
                .fold(
                    onSuccess = {
                        sendIntent(TermsAgreementIntent.SubmitSuccess)
                    },
                    onFailure = { error ->
                        Log.e("TermsAgreement", "Submit failed: ${error.message}")
                        sendIntent(TermsAgreementIntent.SubmitFailure)
                    },
                )
        }
    }
}
