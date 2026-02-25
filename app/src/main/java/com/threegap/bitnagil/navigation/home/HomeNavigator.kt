package com.threegap.bitnagil.navigation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class HomeNavigator(
    val navController: NavHostController,
) {
    val startDestination = HomeRoute.Home.route

    val currentRoute: String?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route

    val isHomeRoute: Boolean
        @Composable get() = currentRoute == HomeRoute.Home.route

    @Composable
    fun shouldShowFloatingAction(): Boolean =
        currentRoute in setOf(HomeRoute.Home.route, HomeRoute.RecommendRoutine.route)
}

@Composable
fun rememberHomeNavigator(navController: NavHostController = rememberNavController()): HomeNavigator =
    remember(navController) {
        HomeNavigator(navController)
    }
