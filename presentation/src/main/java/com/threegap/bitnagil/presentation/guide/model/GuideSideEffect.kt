package com.threegap.bitnagil.presentation.guide.model

sealed interface GuideSideEffect {
    data object NavigateToBack : GuideSideEffect
}
