package com.threegap.bitnagil.navigation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class HomeNavigator(
    val navController: NavHostController,
) {
    val startDestination = HomeRoute.Home.route
}

@Composable
fun rememberHomeNavigator(navController: NavHostController = rememberNavController()): HomeNavigator =
    remember(navController) {
        HomeNavigator(navController)
    }
