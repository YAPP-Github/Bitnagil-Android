package com.threegap.bitnagil

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object Splash : Route

    @Serializable
    data object Intro : Route

    @Serializable
    data object TermsAgreement : Route

    @Serializable
    data object Login : Route

    @Serializable
    data object Home : Route

    @Serializable
    data class WebView(
        val title: String,
        val url: String,
    ) : Route
}
