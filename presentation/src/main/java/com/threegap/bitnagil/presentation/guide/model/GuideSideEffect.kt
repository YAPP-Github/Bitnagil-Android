package com.threegap.bitnagil.presentation.guide.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviSideEffect

sealed interface GuideSideEffect : MviSideEffect {
    data object NavigateToBack : GuideSideEffect
}
