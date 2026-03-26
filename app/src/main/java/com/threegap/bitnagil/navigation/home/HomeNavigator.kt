package com.threegap.bitnagil.navigation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class HomeNavigator(
    val navController: NavHostController,
) {
    val startDestination: HomeRoute = HomeRoute.Home

    val currentHomeRoute: HomeRoute?
        @Composable get() {
            val destination = navController.currentBackStackEntryAsState().value?.destination
            return when {
                destination?.hasRoute(HomeRoute.Home::class) == true -> HomeRoute.Home
                destination?.hasRoute(HomeRoute.RecommendRoutine::class) == true -> HomeRoute.RecommendRoutine
                destination?.hasRoute(HomeRoute.MyPage::class) == true -> HomeRoute.MyPage
                else -> null
            }
        }

    val isHomeRoute: Boolean
        @Composable get() = currentHomeRoute == HomeRoute.Home

    @Composable
    fun shouldShowFloatingAction(): Boolean =
        currentHomeRoute == HomeRoute.Home || currentHomeRoute == HomeRoute.RecommendRoutine

    fun navigateTo(route: HomeRoute) {
        navController.navigate(route) {
            popUpTo(0) { inclusive = true }
        }
    }
}

@Composable
fun rememberHomeNavigator(navController: NavHostController = rememberNavController()): HomeNavigator =
    remember(navController) {
        HomeNavigator(navController)
    }
