package com.threegap.bitnagil.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kakao.sdk.user.UserApiClient
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.presentation.login.model.LoginIntent
import com.threegap.bitnagil.presentation.login.model.LoginSideEffect
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun LoginScreenContainer(
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val client = UserApiClient.instance

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is LoginSideEffect.RequestKakaoTalkLogin -> {
                client.loginWithKakaoTalk(context) { token, error ->
                    viewModel.kakaoLogin(token, error)
                }
            }

            is LoginSideEffect.RequestKakaoAccountLogin -> {
                client.loginWithKakaoAccount(context) { token, error ->
                    viewModel.kakaoLogin(token, error)
                }
            }

            is LoginSideEffect.NavigateToHome -> {
                // TODO: Navigate to Home
            }

            is LoginSideEffect.NavigateToTermsOfService -> {
                // TODO: Navigate to Terms of Service
            }
        }
    }

    LoginScreen(
        onKakaoLoginClick = {
            viewModel.sendIntent(
                LoginIntent.OnKakaoLoginClick(client.isKakaoTalkLoginAvailable(context)),
            )
        },
    )
}

@Composable
private fun LoginScreen(
    onKakaoLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Text(
            text = "빛나길 로고",
            modifier = Modifier.align(Alignment.Center),
        )

        Button(
            onClick = onKakaoLoginClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
        ) {
            Text(
                text = "카카오 로그인버튼",
            )
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    BitnagilTheme {
        LoginScreen(
            onKakaoLoginClick = {},
        )
    }
}
