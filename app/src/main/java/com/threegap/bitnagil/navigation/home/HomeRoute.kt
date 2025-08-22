package com.threegap.bitnagil.navigation.home

import com.threegap.bitnagil.R

enum class HomeRoute(
    val route: String,
    val title: String,
    val icon: Int,
) {
    Home(
        route = "home/home",
        title = "홈",
        icon = R.drawable.ic_home,
    ),

    RecommendRoutine(
        route = "home/recommend_routine",
        title = "추천 루틴",
        icon = R.drawable.ic_routine_recommend,
    ),

    MyPage(
        route = "home/my_page",
        title = "마이페이지",
        icon = R.drawable.ic_profile,
    ),
    ;
}
