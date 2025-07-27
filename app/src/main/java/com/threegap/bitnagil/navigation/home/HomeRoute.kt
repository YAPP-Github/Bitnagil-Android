package com.threegap.bitnagil.navigation.home

import kotlinx.serialization.Serializable

@Serializable
sealed class HomeRoute(val route: String) {
    @Serializable
    data object Home : HomeRoute("home/home")

    @Serializable
    data object RecommendRoutine : HomeRoute("home/recommend_routine")

    @Serializable
    data object MyPage : HomeRoute("home/my_page")
}
