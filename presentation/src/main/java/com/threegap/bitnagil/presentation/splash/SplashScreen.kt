package com.threegap.bitnagil.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
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

    SplashScreen()
}

@Composable
private fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            text = "야무진 로고 추가 예정",
            modifier = Modifier
                .align(Alignment.Center),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreen()
}
