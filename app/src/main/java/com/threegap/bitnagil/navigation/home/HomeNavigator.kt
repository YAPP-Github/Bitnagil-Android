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

    @Composable
    fun getCurrentRoute(): String? {
        return navController.currentBackStackEntryAsState().value?.destination?.route
    }

    @Composable
    fun shouldShowFloatingAction(): Boolean {
        val currentRoute = getCurrentRoute()
        return currentRoute == HomeRoute.Home.route || currentRoute == HomeRoute.RecommendRoutine.route
    }
}

@Composable
fun rememberHomeNavigator(navController: NavHostController = rememberNavController()): HomeNavigator =
    remember(navController) {
        HomeNavigator(navController)
    }
