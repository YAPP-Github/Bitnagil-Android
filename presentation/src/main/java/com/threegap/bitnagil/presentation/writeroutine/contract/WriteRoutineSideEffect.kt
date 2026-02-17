package com.threegap.bitnagil.presentation.writeroutine.contract

sealed interface WriteRoutineSideEffect {
    data object MoveToPreviousScreen : WriteRoutineSideEffect
    data class ShowToast(val message: String) : WriteRoutineSideEffect
}
