package com.threegap.bitnagil.presentation.onboarding.model.mvi

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviSideEffect

sealed class OnBoardingSideEffect : MviSideEffect {
    data object MoveToPreviousScreen : OnBoardingSideEffect()
    data object NavigateToHomeScreen : OnBoardingSideEffect()
}
