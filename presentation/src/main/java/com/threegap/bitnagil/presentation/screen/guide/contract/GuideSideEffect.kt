package com.threegap.bitnagil.presentation.screen.guide.contract

sealed interface GuideSideEffect {
    data object NavigateToBack : GuideSideEffect
}
