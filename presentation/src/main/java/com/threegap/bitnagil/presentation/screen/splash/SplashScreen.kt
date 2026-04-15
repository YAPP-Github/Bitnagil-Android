package com.threegap.bitnagil.presentation.screen.splash

import androidx.activity.ComponentActivity
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.presentation.screen.splash.component.template.BitnagilLottieAnimation
import com.threegap.bitnagil.presentation.screen.splash.component.template.ForceUpdateDialog
import com.threegap.bitnagil.presentation.screen.splash.contract.SplashSideEffect
import com.threegap.bitnagil.presentation.util.playstore.openAppInPlayStore
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import kotlin.system.exitProcess

@Composable
fun SplashScreenContainer(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateToTermsAgreement: () -> Unit,
    navigateToOnboarding: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val context = LocalContext.current
    val activity = context as? ComponentActivity
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SplashSideEffect.NavigateToLogin -> navigateToLogin()
            is SplashSideEffect.NavigateToTermsAgreement -> navigateToTermsAgreement()
            is SplashSideEffect.NavigateToOnboarding -> navigateToOnboarding()
            is SplashSideEffect.NavigateToHome -> navigateToHome()
        }
    }

    SplashScreen(
        onCompleted = viewModel::onAnimationCompleted,
    )

    if (uiState.forceUpdateRequired) {
        ForceUpdateDialog(
            onUpdateClick = { openAppInPlayStore(activity) },
            onDismissRequest = {
                activity?.finishAffinity()
                exitProcess(0)
            },
        )
    }
}

@Composable
private fun SplashScreen(
    onCompleted: () -> Unit,
) {
    var showIcon by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            BitnagilLottieAnimation(
                lottieJson = R.raw.splash_lottie,
                onComplete = onCompleted,
                onStart = { showIcon = true },
                maxFrame = 120,
                modifier = Modifier
                    .padding(bottom = 36.dp)
                    .size(204.dp)
                    .align(Alignment.BottomCenter),
            )

            androidx.compose.animation.AnimatedVisibility(
                visible = showIcon,
                enter = fadeIn(animationSpec = tween(500)),
                modifier = Modifier.align(Alignment.BottomCenter),
            ) {
                BitnagilIcon(id = R.drawable.img_app_name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreen(
        onCompleted = {},
    )
}
