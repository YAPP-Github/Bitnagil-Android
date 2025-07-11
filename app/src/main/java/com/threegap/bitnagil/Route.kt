package com.threegap.bitnagil

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object Splash : Route

    @Serializable
    data object Intro : Route

    @Serializable
    data object Login : Route

    @Serializable
    data object Home : Route
}
