package com.threegap.bitnagil.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.presentation.login.kakao.KakaoLoginHandlerImpl
import com.threegap.bitnagil.presentation.login.model.LoginSideEffect
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun LoginScreenContainer(
    navigateToHome: () -> Unit,
    navigateToTermsAgreement: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is LoginSideEffect.NavigateToHome -> {
                navigateToHome()
            }

            is LoginSideEffect.NavigateToTermsAgreement -> {
                navigateToTermsAgreement()
            }
        }
    }

    LoginScreen(
        onKakaoLoginClick = {
            KakaoLoginHandlerImpl.login(context) { token, error ->
                viewModel.kakaoLogin(token, error)
            }
        },
    )
}

@Composable
private fun LoginScreen(
    onKakaoLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val windowInfo = LocalWindowInfo.current
    val screenHeight = with(LocalDensity.current) {
        windowInfo.containerSize.height.toDp()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(BitnagilTheme.colors.white),
    ) {
        Spacer(modifier = Modifier.height(screenHeight * 0.0748f))

        Text(
            text = "빛나길에 오신걸 환영해요!",
            color = BitnagilTheme.colors.navy500,
            style = BitnagilTheme.typography.title2Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(screenHeight * 0.185f))

        Image(
            painter = painterResource(R.drawable.intro_character),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(50.dp)
                .aspectRatio(260f / 295f),
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 20.dp)
                .height(54.dp)
                .clickableWithoutRipple { onKakaoLoginClick() }
                .background(
                    color = BitnagilTheme.colors.kakao,
                    shape = RoundedCornerShape(12.dp),
                )
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            BitnagilIcon(
                id = R.drawable.ic_kakao_login,
                modifier = Modifier.padding(end = 8.dp),
            )
            Text(
                text = "카카오로 시작하기",
                color = BitnagilTheme.colors.black,
                style = BitnagilTheme.typography.button2,
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
