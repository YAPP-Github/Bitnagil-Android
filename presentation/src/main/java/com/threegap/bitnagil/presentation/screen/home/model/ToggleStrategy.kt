package com.threegap.bitnagil.presentation.screen.home.model

sealed interface ToggleStrategy {
    data object Main : ToggleStrategy
    data class Sub(val index: Int) : ToggleStrategy
}
