package com.threegap.bitnagil

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.threegap.bitnagil.presentation.home.HomeScreen
import com.threegap.bitnagil.presentation.intro.IntroScreenContainer
import com.threegap.bitnagil.presentation.login.LoginScreenContainer
import com.threegap.bitnagil.presentation.splash.SplashScreenContainer
import com.threegap.bitnagil.presentation.terms.TermsAgreementScreenContainer

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
        composable<Route.Splash> {
            SplashScreenContainer(
                navigateToIntro = { navigator.navController.navigate(Route.Intro) },
                navigateToHome = { navigator.navController.navigate(Route.Home) },
            )
        }

        composable<Route.Intro> {
            IntroScreenContainer(
                navigateToLogin = { navigator.navController.navigate(Route.Login) },
            )
        }

        composable<Route.Login> {
            LoginScreenContainer(
                navigateToHome = { navigator.navController.navigate(Route.Home) },
                navigateToTermsAgreement = { navigator.navController.navigate(Route.TermsAgreement) },
            )
        }

        composable<Route.TermsAgreement> {
            TermsAgreementScreenContainer(
                navigateToOnBoarding = { },
                navigateToBack = { navigator.navController.popBackStack() },
            )
        }

        composable<Route.Home> {
            HomeScreen()
        }
    }
}
