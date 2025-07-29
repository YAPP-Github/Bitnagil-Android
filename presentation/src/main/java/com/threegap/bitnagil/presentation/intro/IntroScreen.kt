package com.threegap.bitnagil.presentation.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.presentation.intro.model.IntroIntent
import com.threegap.bitnagil.presentation.intro.model.IntroSideEffect
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun IntroScreenContainer(
    navigateToLogin: () -> Unit,
    viewModel: IntroViewModel = hiltViewModel(),
) {
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is IntroSideEffect.NavigateToLogin -> navigateToLogin()
        }
    }

    IntroScreen(
        onStartButtonClick = {
            viewModel.sendIntent(IntroIntent.OnStartButtonClick)
        },
    )
}

@Composable
private fun IntroScreen(
    onStartButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val windowInfo = LocalWindowInfo.current
    val screenHeight = with(LocalDensity.current) {
        windowInfo.containerSize.height.toDp()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(screenHeight * 0.0748f))

        Text(
            text = "당신의 하루 리듬을 이해하고,\n작은 변화를 함께 시작해볼게요.",
            color = BitnagilTheme.colors.navy500,
            style = BitnagilTheme.typography.title2Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(screenHeight * 0.151f))

        Image(
            painter = painterResource(R.drawable.intro_character),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(50.dp)
                .aspectRatio(260f / 295f),
        )

        Spacer(modifier = Modifier.weight(1f))

        BitnagilTextButton(
            text = "시작하기",
            onClick = onStartButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 20.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IntroScreenPreview() {
    IntroScreen(
        onStartButtonClick = {},
    )
}
