package com.threegap.bitnagil

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.threegap.bitnagil.navigation.home.HomeNavHost
import com.threegap.bitnagil.presentation.emotion.EmotionScreenContainer
import com.threegap.bitnagil.presentation.intro.IntroScreenContainer
import com.threegap.bitnagil.presentation.login.LoginScreenContainer
import com.threegap.bitnagil.presentation.onboarding.OnBoardingScreenContainer
import com.threegap.bitnagil.presentation.onboarding.OnBoardingViewModel
import com.threegap.bitnagil.presentation.onboarding.model.navarg.OnBoardingScreenArg
import com.threegap.bitnagil.presentation.setting.SettingScreenContainer
import com.threegap.bitnagil.presentation.splash.SplashScreenContainer
import com.threegap.bitnagil.presentation.terms.TermsAgreementScreenContainer
import com.threegap.bitnagil.presentation.webview.BitnagilWebViewScreen
import com.threegap.bitnagil.presentation.writeroutine.WriteRoutineScreenContainer
import com.threegap.bitnagil.presentation.writeroutine.WriteRoutineViewModel
import com.threegap.bitnagil.presentation.writeroutine.model.navarg.WriteRoutineScreenArg

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
                navigateToIntro = {
                    navigator.navController.navigate(Route.Intro) {
                        popUpTo<Route.Splash> { inclusive = true }
                    }
                },
                navigateToHome = {
                    navigator.navController.navigate(Route.Home) {
                        popUpTo(navigator.navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
            )
        }

        composable<Route.Intro> {
            IntroScreenContainer(
                navigateToLogin = { navigator.navController.navigate(Route.Login) },
            )
        }

        composable<Route.Login> {
            LoginScreenContainer(
                navigateToHome = {
                    navigator.navController.navigate(Route.Home) {
                        popUpTo(navigator.navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
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
                navigateToOnBoarding = {
                    navigator.navController.navigate(Route.OnBoarding())
                },
                navigateToBack = { navigator.navController.popBackStack() },
            )
        }

        composable<Route.Home> {
            HomeNavHost(
                navigateToSetting = {
                    navigator.navController.navigate(Route.Setting)
                },
                navigateToOnBoarding = {
                    navigator.navController.navigate(Route.OnBoarding(isNew = false))
                },
                navigateToNotice = {
                    navigator.navController.navigate(
                        Route.WebView(
                            title = "공지 사항",
                            url = "https://complex-wombat-99f.notion.site/23ff4587491d80efa0a5e4baece6017b?source=copy_link",
                        )
                    )
                },
                navigateToQnA = {
                    navigator.navController.navigate(
                        Route.WebView(
                            title = "자주 묻는 질문",
                            url = "https://complex-wombat-99f.notion.site/23ff4587491d80659ae3ea392afbc05e?source=copy_link",
                        )
                    )
                },
                navigateToRegisterRoutine = { routineId ->
                    navigator.navController.navigate(Route.WriteRoutine(routineId = routineId))
                },
                navigateToEditRoutine = { routineId ->
                    navigator.navController.navigate(Route.WriteRoutine(routineId = routineId, isRegister = false))
                },
                navigateToEmotion = {
                    navigator.navController.navigate(Route.Emotion)
                },
            )
        }

        composable<Route.WebView> {
            val webViewRoute = it.toRoute<Route.WebView>()
            BitnagilWebViewScreen(
                title = webViewRoute.title,
                url = webViewRoute.url,
                onBackClick = { navigator.navController.popBackStack() },
            )
        }

        composable<Route.Setting> {
            SettingScreenContainer(
                navigateToBack = {
                    navigator.navController.popBackStack()
                },
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
                navigateToIntro = {
                    navigator.navController.navigate(Route.Intro) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                },
            )
        }

        composable<Route.OnBoarding> { navBackStackEntry ->
            val arg = navBackStackEntry.toRoute<Route.OnBoarding>()
            val onBoardingScreenArg = if (arg.isNew) {
                OnBoardingScreenArg.NEW
            } else {
                OnBoardingScreenArg.RESET
            }

            val viewModel = hiltViewModel<OnBoardingViewModel, OnBoardingViewModel.Factory> { factory ->
                factory.create(onBoardingScreenArg)
            }

            OnBoardingScreenContainer(
                onBoardingViewModel = viewModel,
                navigateToHome = {
                    navigator.navController.navigate(Route.Home) {
                        popUpTo(navigator.navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                navigateToBack = {
                    navigator.navController.popBackStack()
                },
            )
        }

        composable<Route.WriteRoutine> { navBackStackEntry ->
            val arg = navBackStackEntry.toRoute<Route.WriteRoutine>()
            val writeScreenNavArg = if (arg.isRegister) {
                WriteRoutineScreenArg.Add(baseRoutineId = arg.routineId)
            } else {
                WriteRoutineScreenArg.Edit(routineId = arg.routineId!!)
            }

            val viewModel = hiltViewModel<WriteRoutineViewModel, WriteRoutineViewModel.Factory> { factory ->
                factory.create(writeScreenNavArg)
            }

            WriteRoutineScreenContainer(
                viewModel = viewModel,
                navigateToBack = {
                    navigator.navController.popBackStack()
                },
            )
        }

        composable<Route.Emotion> {
            EmotionScreenContainer(
                navigateToBack = {
                    navigator.navController.popBackStack()
                },
            )
        }
    }
}
