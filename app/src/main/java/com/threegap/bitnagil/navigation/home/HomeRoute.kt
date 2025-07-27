package com.threegap.bitnagil.navigation.home

import com.threegap.bitnagil.R

enum class HomeRoute(
    val route: String,
    val title: String,
    val selectIconResourceId: Int,
    val unSelectIconResourceId: Int,
) {
    Home(
        route = "home/home",
        title = "홈",
        selectIconResourceId = R.drawable.ic_home_fill,
        unSelectIconResourceId = R.drawable.ic_home_empty,
    ),

    RecommendRoutine(
        route = "home/recommend_routine",
        title = "추천 루틴",
        selectIconResourceId = R.drawable.ic_recommend_fill,
        unSelectIconResourceId = R.drawable.ic_recommend_empty,
    ),

    MyPage(
        route = "home/my_page",
        title = "마이페이지",
        selectIconResourceId = R.drawable.ic_mypage_fill,
        unSelectIconResourceId = R.drawable.ic_mypage_empty,
    ),
    ;
}
