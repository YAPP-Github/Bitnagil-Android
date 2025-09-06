package com.threegap.bitnagil.presentation.emotion.component.template

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.atom.BitnagilSelectButton
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButtonColor
import com.threegap.bitnagil.presentation.emotion.model.EmotionImageUiModel
import com.threegap.bitnagil.presentation.emotion.model.EmotionRecommendRoutineUiModel
import com.threegap.bitnagil.presentation.emotion.model.EmotionScreenStep
import com.threegap.bitnagil.presentation.emotion.model.EmotionUiModel
import com.threegap.bitnagil.presentation.emotion.model.mvi.EmotionState

@Composable
fun EmotionRecommendRoutineScreen(
    state: EmotionState,
    onClickRoutine: (String) -> Unit,
    onClickRegisterRecommendRoutines: () -> Unit,
    onClickSkip: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BitnagilTheme.colors.coolGray99)
            .statusBarsPadding()
            .padding(start = 16.dp, end = 16.dp, bottom = 20.dp, top = 32.dp),
    ) {
        Spacer(modifier = Modifier.height(54.dp))

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
            modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    BitnagilTheme {
        EmotionRecommendRoutineScreen(
            state = EmotionState(
                emotionTypeUiModels = listOf(
                    EmotionUiModel(
                        emotionType = "emotionType",
                        emotionMarbleName = "emotionMarbleName",
                        image = EmotionImageUiModel.Url(
                            url = "https://bitnagil-s3.s3.ap-northeast-2.amazonaws.com/home_satisfaction.png",
                            offlineBackupImageResourceId = null,
                        ),
                    ),
                ),
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
            onClickRoutine = {},
            onClickRegisterRecommendRoutines = {},
            onClickSkip = {},
        )
    }
}
