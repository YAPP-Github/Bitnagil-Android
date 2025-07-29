package com.threegap.bitnagil.presentation.emotion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.presentation.common.flow.collectAsEffect
import com.threegap.bitnagil.presentation.emotion.model.Emotion
import com.threegap.bitnagil.presentation.emotion.model.mvi.EmotionSideEffect
import com.threegap.bitnagil.presentation.emotion.model.mvi.EmotionState

@Composable
fun EmotionScreenContainer(
    viewModel: EmotionViewModel = hiltViewModel(),
    navigateToBack: () -> Unit,
) {
    val state by viewModel.stateFlow.collectAsState()

    viewModel.sideEffectFlow.collectAsEffect { sideEffect ->
        when (sideEffect) {
            EmotionSideEffect.NavigateToBack -> navigateToBack()
        }
    }

    EmotionScreen(
        state = state,
        onClickPreviousButton = navigateToBack,
        onClickEmotion = viewModel::selectEmotion,
    )
}

@Composable
private fun EmotionScreen(
    state: EmotionState,
    onClickPreviousButton: () -> Unit,
    onClickEmotion: (Emotion) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BitnagilTheme.colors.white),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BitnagilTopBar(
            showBackButton = true,
            onBackClick = onClickPreviousButton,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            "오늘의 감정구슬을 골라보세요",
            style = BitnagilTheme.typography.title2Bold.copy(color = BitnagilTheme.colors.navy500),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            "감정구슬을 등록하면 루틴을 추천받아요!",
            style = BitnagilTheme.typography.subtitle1Regular.copy(color = BitnagilTheme.colors.navy300),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(64.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp),
        ) {
            items(state.emotions) { emotion ->
                Column(
                    modifier = Modifier
                        .clickableWithoutRipple { onClickEmotion(emotion) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(id = emotion.imageResourceId),
                        contentDescription = null,
                    )
                    Text(
                        text = emotion.emotionName,
                        style = BitnagilTheme.typography.body1Regular.copy(color = BitnagilTheme.colors.coolGray20),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MyPageScreenPreview() {
    BitnagilTheme {
        EmotionScreen(
            state = EmotionState(
                emotions = Emotion.entries,
                isLoading = false,
            ),
            onClickEmotion = { _ -> },
            onClickPreviousButton = {},
        )
    }
}
