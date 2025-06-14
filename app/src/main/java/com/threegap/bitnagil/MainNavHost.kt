package com.threegap.bitnagil

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.threegap.bitnagil.presentation.home.HomeScreen
import com.threegap.bitnagil.presentation.login.LoginScreen

@Composable
fun MainNavHost(
    navigator: MainNavigator,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        modifier = modifier,
    ) {
        composable<Route.Login> {
            LoginScreen(
                onLoginClick = { navigator.navController.navigate(Route.Home) },
            )
        }

        composable<Route.Home> {
            HomeScreen()
        }
    }
}
