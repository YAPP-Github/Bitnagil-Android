package com.threegap.bitnagil.presentation.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = "당신의 하루 리듬을 이해하고,\n작은 변화를 함께 시작해볼게요.",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp),
        )

        Spacer(
            modifier = Modifier.height(38.dp),
        )

        Box(
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
                .height(458.dp),
        ) {
            Text(
                text = "빛나길 로고",
            )
        }

        Spacer(
            modifier = Modifier.height(38.dp),
        )

        Button(
            onClick = onStartButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
        ) {
            Text("시작하기")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun IntroScreenPreview() {
    IntroScreen(
        onStartButtonClick = {},
    )
}
