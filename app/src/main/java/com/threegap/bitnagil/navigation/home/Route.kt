package com.threegap.bitnagil.navigation.home

import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeRoute {
    @Serializable
    data object Home : HomeRoute

    @Serializable
    data object RecommendRoutine : HomeRoute

    @Serializable
    data object MyPage : HomeRoute
}
