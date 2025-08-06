package com.threegap.bitnagil

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class MainNavigator(
    val navController: NavHostController,
) {
    val startDestination = Route.Splash

    internal fun navigateToHomeAndClearStack() =
        navController.navigate(Route.Home) {
            popUpTo(0) {
                inclusive = true
            }
        }
}

@Composable
fun rememberMainNavigator(navController: NavHostController = rememberNavController()): MainNavigator =
    remember(navController) {
        MainNavigator(navController)
    }
