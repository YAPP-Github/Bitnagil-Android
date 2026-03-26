package com.threegap.bitnagil.domain.routine.model

sealed interface ToggleStrategy {
    data object Main : ToggleStrategy
    data class Sub(val index: Int) : ToggleStrategy
}
