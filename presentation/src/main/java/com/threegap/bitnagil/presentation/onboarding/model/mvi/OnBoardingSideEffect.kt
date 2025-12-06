package com.threegap.bitnagil.presentation.onboarding.model.mvi

sealed class OnBoardingSideEffect {
    data object MoveToPreviousScreen : OnBoardingSideEffect()
    data object NavigateToHomeScreen : OnBoardingSideEffect()
    data class ShowToast(val message: String) : OnBoardingSideEffect()
}
