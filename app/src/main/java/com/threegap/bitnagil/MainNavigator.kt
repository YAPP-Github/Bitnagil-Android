package com.threegap.bitnagil

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class MainNavigator(
    val navController: NavHostController,
) {
    val startDestination = Route.Splash
}

@Composable
fun rememberMainNavigator(navController: NavHostController = rememberNavController()): MainNavigator =
    remember(navController) {
        MainNavigator(navController)
    }
