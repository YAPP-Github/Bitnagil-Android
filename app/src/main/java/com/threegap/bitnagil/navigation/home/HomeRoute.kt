package com.threegap.bitnagil.navigation.home

import com.threegap.bitnagil.R
import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeRoute {
    val showFloatingButton: Boolean

    @Serializable
    data object Home : HomeRoute {
        override val showFloatingButton: Boolean = true
    }

    @Serializable
    data object RecommendRoutine : HomeRoute {
        override val showFloatingButton: Boolean = true
    }

    @Serializable
    data object MyPage : HomeRoute {
        override val showFloatingButton: Boolean = false
    }
}

data class HomeTab(
    val route: HomeRoute,
    val title: String,
    val icon: Int,
)

val homeTabList = listOf(
    HomeTab(HomeRoute.Home, "홈", R.drawable.ic_home),
    HomeTab(HomeRoute.RecommendRoutine, "추천 루틴", R.drawable.ic_routine_recommend),
    HomeTab(HomeRoute.MyPage, "마이페이지", R.drawable.ic_profile),
)
