package com.threegap.bitnagil.presentation.routinelist.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviSideEffect

sealed interface RoutineListSideEffect : MviSideEffect {
    data object NavigateToBack : RoutineListSideEffect
}
