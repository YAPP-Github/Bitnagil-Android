package com.threegap.bitnagil

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainNavigator(
    val navController: NavHostController,
) {
    val startDestination = Route.Splash

    private val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry by navController.currentBackStackEntryAsState()
            return currentEntry?.destination
        }

    internal fun navigateToHomeAndClearStack() =
        navController.navigate(Route.Home) {
            popUpTo(0) {
                inclusive = true
            }
        }

    @Composable
    internal fun hasBottomNavigationBarRoute(): Boolean {
        val destination = currentDestination
        return when {
            destination?.hasRoute<Route.Home>() == true -> true
            else -> false
        }
    }
}

@Composable
fun rememberMainNavigator(navController: NavHostController = rememberNavController()): MainNavigator =
    remember(navController) {
        MainNavigator(navController)
    }
