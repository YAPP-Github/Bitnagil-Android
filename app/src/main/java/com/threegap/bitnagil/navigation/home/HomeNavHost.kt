package com.threegap.bitnagil.navigation.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    navigateToRegisterRoutine: (String?) -> Unit,
    navigateToEditRoutine: (String) -> Unit,
    navigateToEmotion: () -> Unit,
) {
    val navigator = rememberHomeNavigator()

    DoubleBackButtonPressedHandler()

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
                composable(HomeRoute.Home.route) {
                    HomeScreenContainer(
                        navigateToRegisterRoutine = {
                            navigateToRegisterRoutine(null)
                        },
                        navigateToEditRoutine = navigateToEditRoutine,
                        navigateToEmotion = navigateToEmotion,
                    )
                }

                composable(HomeRoute.RecommendRoutine.route) {
                    RecommendRoutineScreenContainer(
                        navigateToEmotion = navigateToEmotion,
                        navigateToRegisterRoutine = navigateToRegisterRoutine
                    )
                }

                composable(HomeRoute.MyPage.route) {
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

@Composable
fun DoubleBackButtonPressedHandler() {
    val context = LocalContext.current
    var backPressedTimeMillis by remember { mutableLongStateOf(0L) }
    BackHandler {
        if (System.currentTimeMillis() - backPressedTimeMillis < 2000) {
            backPressedTimeMillis = 0
            (context as? Activity)?.finish()
        } else {
            backPressedTimeMillis = System.currentTimeMillis()
        }
    }
}
