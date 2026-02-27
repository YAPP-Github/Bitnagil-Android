package com.threegap.bitnagil.presentation.screen.onboarding.contract

sealed interface OnBoardingSideEffect {
    data object MoveToPreviousScreen : OnBoardingSideEffect
    data object NavigateToHomeScreen : OnBoardingSideEffect
    data class ShowToast(val message: String) : OnBoardingSideEffect
}
