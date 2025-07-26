package com.threegap.bitnagil.navigation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.threegap.bitnagil.presentation.home.HomeScreenContainer
import com.threegap.bitnagil.presentation.mypage.MyPageScreenContainer
import com.threegap.bitnagil.presentation.recommendroutine.RecommendRoutineScreenContainer


@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun HomeNavHost(
    modifier: Modifier = Modifier,
    navigateToSetting: () -> Unit,
    navigateToOnBoarding: () -> Unit,
    navigateToNotice: () -> Unit,
    navigateToQnA: () -> Unit,
    navigateToRegisterRoutine: () -> Unit,
    navigateToEditRoutine: (String) -> Unit,
    navigateToEmotion: () -> Unit,
) {
    val navigator = rememberHomeNavigator()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            HomeBottomNavigationBar(navController = navigator.navController)
        },
        content = { _ ->
            NavHost(
                navController = navigator.navController,
                startDestination = navigator.startDestination,
                modifier = modifier,
            ) {
                composable<HomeRoute.Home> {
                    HomeScreenContainer(
                        navigateToRegisterRoutine = navigateToRegisterRoutine,
                        navigateToEditRoutine = navigateToEditRoutine,
                        navigateToEmotion = navigateToEmotion,
                    )
                }

                composable<HomeRoute.RecommendRoutine> {
                    RecommendRoutineScreenContainer()
                }

                composable<HomeRoute.MyPage> {
                    MyPageScreenContainer(
                        navigateToSetting = navigateToSetting,
                        navigateToOnBoarding = navigateToOnBoarding,
                        navigateToNotice = navigateToNotice,
                        navigateToQnA = navigateToQnA
                    )
                }
            }
        }
    )
}
