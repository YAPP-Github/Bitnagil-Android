package com.threegap.bitnagil.presentation.screen.routinelist.contract

sealed interface RoutineListSideEffect {
    data object NavigateToBack : RoutineListSideEffect
    data object NavigateToAddRoutine : RoutineListSideEffect
    data class NavigateToEditRoutine(val routineId: String, val updateRoutineFromNowDate: Boolean) : RoutineListSideEffect
    data class ShowToast(val message: String) : RoutineListSideEffect
}
