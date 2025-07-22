package com.threegap.bitnagil

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.threegap.bitnagil.presentation.home.HomeScreen
import com.threegap.bitnagil.presentation.intro.IntroScreenContainer
import com.threegap.bitnagil.presentation.login.LoginScreenContainer
import com.threegap.bitnagil.presentation.splash.SplashScreenContainer
import com.threegap.bitnagil.presentation.terms.TermsAgreementScreenContainer
import com.threegap.bitnagil.presentation.webview.BitnagilWebViewScreen

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
                navigateToTermsOfService = {
                    navigator.navController.navigate(
                        Route.WebView(
                            title = "약관 동의",
                            url = "https://complex-wombat-99f.notion.site/2025-7-20-236f4587491d8071833adfaf8115bce2",
                        ),
                    )
                },
                navigateToPrivacyPolicy = {
                    navigator.navController.navigate(
                        Route.WebView(
                            title = "약관 동의",
                            url = "https://complex-wombat-99f.notion.site/2025-07-20-236f4587491d80308016eb810692d18b",
                        ),
                    )
                },
                navigateToOnBoarding = { },
                navigateToBack = { navigator.navController.popBackStack() },
            )
        }

        composable<Route.Home> {
            HomeScreen()
        }

        composable<Route.WebView> {
            val webViewRoute = it.toRoute<Route.WebView>()
            BitnagilWebViewScreen(
                title = webViewRoute.title,
                url = webViewRoute.url,
                onBackClick = { navigator.navController.popBackStack() },
            )
        }
    }
}
