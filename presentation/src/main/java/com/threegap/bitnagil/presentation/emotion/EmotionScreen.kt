package com.threegap.bitnagil.presentation.emotion

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.atom.BitnagilSelectButton
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButtonColor
import com.threegap.bitnagil.designsystem.component.block.BitnagilProgressTopBar
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.presentation.common.flow.collectAsEffect
import com.threegap.bitnagil.presentation.emotion.model.Emotion
import com.threegap.bitnagil.presentation.emotion.model.EmotionRecommendRoutineUiModel
import com.threegap.bitnagil.presentation.emotion.model.EmotionScreenStep
import com.threegap.bitnagil.presentation.emotion.model.mvi.EmotionSideEffect
import com.threegap.bitnagil.presentation.emotion.model.mvi.EmotionState

@Composable
fun EmotionScreenContainer(
    viewModel: EmotionViewModel = hiltViewModel(),
    navigateToBack: () -> Unit,
) {
    val state by viewModel.stateFlow.collectAsState()

    BackHandler {
        viewModel.moveToPrev()
    }

    viewModel.sideEffectFlow.collectAsEffect { sideEffect ->
        when (sideEffect) {
            EmotionSideEffect.NavigateToBack -> navigateToBack()
        }
    }

    when (state.step) {
        EmotionScreenStep.Emotion -> EmotionScreen(
            state = state,
            onClickPreviousButton = navigateToBack,
            onClickEmotion = viewModel::selectEmotion,
        )
        EmotionScreenStep.RecommendRoutines -> EmotionRecommendRoutineScreen(
            state = state,
            onClickPreviousButton = viewModel::moveToPrev,
            onClickRoutine = viewModel::selectRecommendRoutine,
            onClickRegisterRecommendRoutines = viewModel::registerRecommendRoutines,
            onClickSkip = navigateToBack,
        )
    }
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

@Composable
private fun EmotionRecommendRoutineScreen(
    state: EmotionState,
    onClickPreviousButton: () -> Unit,
    onClickRoutine: (String) -> Unit,
    onClickRegisterRecommendRoutines: () -> Unit,
    onClickSkip: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BitnagilTheme.colors.coolGray99),
    ) {
        BitnagilProgressTopBar(
            onBackClick = onClickPreviousButton,
            progress = 1f,
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp, bottom = 20.dp, top = 32.dp),
        ) {
            Text(
                text = "오늘 감정에 따른\n루틴을 추천드릴께요!",
                color = BitnagilTheme.colors.navy500,
                style = BitnagilTheme.typography.title2Bold,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "오늘 당신의 감정 상태에 맞춰 구성된 맞춤 루틴이에요.\n원하는 루틴을 선택해서 가볍게 시작해보세요.",
                color = BitnagilTheme.colors.coolGray50,
                style = BitnagilTheme.typography.body2Medium,
            )

            Spacer(modifier = Modifier.height(28.dp))

            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(state = scrollState),
            ) {
                for (recommendRoutine in state.recommendRoutines) {
                    BitnagilSelectButton(
                        title = recommendRoutine.name,
                        description = recommendRoutine.description,
                        onClick = { onClickRoutine(recommendRoutine.id) },
                        selected = recommendRoutine.selected,
                        modifier = Modifier.padding(bottom = 12.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            BitnagilTextButton(
                text = "변경하기",
                onClick = onClickRegisterRecommendRoutines,
                enabled = state.registerRecommendRoutinesButtonEnabled,
            )

            Spacer(modifier = Modifier.height(10.dp))

            BitnagilTextButton(
                text = "건너뛰기",
                onClick = onClickSkip,
                colors = BitnagilTextButtonColor.skip().copy(
                    defaultBackgroundColor = Color.Transparent,
                    pressedBackgroundColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent,
                ),
                textStyle = BitnagilTheme.typography.body2Regular,
                textDecoration = TextDecoration.Underline,
            )
        }
    }
}

@Preview
@Composable
private fun EmotionScreenPreview() {
    BitnagilTheme {
        EmotionScreen(
            state = EmotionState(
                emotions = Emotion.entries,
                isLoading = false,
                step = EmotionScreenStep.Emotion,
            ),
            onClickEmotion = { _ -> },
            onClickPreviousButton = {},
        )
    }
}

@Preview
@Composable
private fun EmotionRecommendRoutineScreenPreview() {
    BitnagilTheme {
        EmotionRecommendRoutineScreen(
            state = EmotionState(
                emotions = Emotion.entries,
                isLoading = false,
                step = EmotionScreenStep.RecommendRoutines,
                recommendRoutines = listOf(
                    EmotionRecommendRoutineUiModel(
                        id = "1",
                        name = "루틴 이름",
                        description = "루틴 설명",
                        selected = true,
                    ),
                ),
            ),
            onClickPreviousButton = {},
            onClickRoutine = {},
            onClickRegisterRecommendRoutines = {},
            onClickSkip = {},
        )
    }
}
