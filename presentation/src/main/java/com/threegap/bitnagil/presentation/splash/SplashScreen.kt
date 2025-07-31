package com.threegap.bitnagil.presentation.splash

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.presentation.splash.component.template.BitnagilLottieAnimation
import com.threegap.bitnagil.presentation.splash.model.SplashSideEffect
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SplashScreenContainer(
    navigateToIntro: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SplashSideEffect.NavigateToIntro -> navigateToIntro()
            is SplashSideEffect.NavigateToHome -> navigateToHome()
        }
    }

    SplashScreen(
        onCompleted = viewModel::onAnimationCompleted,
    )
}

@Composable
private fun SplashScreen(
    onCompleted: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showIcon by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BitnagilTheme.colors.white),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            BitnagilLottieAnimation(
                lottieJson = R.raw.splash_lottie,
                onComplete = onCompleted,
                onStart = {
                    showIcon = true
                },
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
