package com.threegap.bitnagil.presentation.writeroutine.model.mvi

sealed class WriteRoutineSideEffect {
    data object MoveToPreviousScreen : WriteRoutineSideEffect()
    data class ShowToast(val message: String) : WriteRoutineSideEffect()
}
