package com.threegap.bitnagil.presentation.guide.contract

sealed interface GuideSideEffect {
    data object NavigateToBack : GuideSideEffect
}
