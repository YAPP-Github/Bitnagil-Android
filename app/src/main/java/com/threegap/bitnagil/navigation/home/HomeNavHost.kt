package com.threegap.bitnagil.navigation.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilFloatingActionMenu
import com.threegap.bitnagil.designsystem.component.atom.FloatingActionItem
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.presentation.common.toast.GlobalBitnagilToast
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
    var showFloatingOverlay by remember { mutableStateOf(false) }

    DoubleBackButtonPressedHandler()

    Box(modifier = Modifier.fillMaxSize()) {
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
                            navigateToRegisterRoutine = navigateToRegisterRoutine,
                        )
                    }

                    composable(HomeRoute.MyPage.route) {
                        MyPageScreenContainer(
                            navigateToSetting = navigateToSetting,
                            navigateToOnBoarding = navigateToOnBoarding,
                            navigateToNotice = navigateToNotice,
                            navigateToQnA = navigateToQnA,
                        )
                    }
                }
            },
        )

        if (navigator.shouldShowFloatingAction()) {
            if (showFloatingOverlay) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BitnagilTheme.colors.black.copy(alpha = 0.7f))
                        .clickableWithoutRipple { showFloatingOverlay = false },
                )
            }

            BitnagilFloatingActionMenu(
                actions = listOf(
                    FloatingActionItem(
                        icon = R.drawable.ic_report,
                        text = "제보하기",
                        onClick = {},
                    ),
                    FloatingActionItem(
                        icon = R.drawable.ic_add_routine,
                        text = "루틴 등록",
                        onClick = { navigateToRegisterRoutine(null) },
                    ),
                ),
                isExpanded = showFloatingOverlay,
                onToggle = { expanded -> showFloatingOverlay = expanded },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 80.dp),
            )
        }
    }
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
            GlobalBitnagilToast.showWarning("버튼을 한 번 더 누르면 종료됩니다.")
        }
    }
}
