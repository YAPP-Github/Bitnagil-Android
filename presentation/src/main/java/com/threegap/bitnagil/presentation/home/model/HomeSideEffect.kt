package com.threegap.bitnagil.presentation.home.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviSideEffect

sealed interface HomeSideEffect : MviSideEffect {
    data class ProcessRoutineToggle(val originalState: HomeState) : HomeSideEffect
}
