package com.threegap.bitnagil.navigation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.threegap.bitnagil.presentation.home.HomeScreenContainer
import com.threegap.bitnagil.presentation.mypage.MyPageScreenContainer
import com.threegap.bitnagil.presentation.recommendroutine.RecommendRoutineScreenContainer

@Composable
fun HomeNavHost(
    navigator: HomeNavigator,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        modifier = modifier,
    ) {
        composable<HomeRoute.Home> {
            HomeScreenContainer()
        }

        composable<HomeRoute.RecommendRoutine> {
            RecommendRoutineScreenContainer()
        }

        composable<HomeRoute.MyPage> {
            MyPageScreenContainer(
                navigateToSetting = {},
                navigateToOnBoarding = {},
                navigateToNotice = {},
                navigateToQnA = {}
            )
        }
    }
}
