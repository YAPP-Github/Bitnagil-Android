package com.threegap.bitnagil.presentation.writeroutine.model.mvi

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviSideEffect

sealed class WriteRoutineSideEffect : MviSideEffect {
    data object MoveToPreviousScreen : WriteRoutineSideEffect()
}
