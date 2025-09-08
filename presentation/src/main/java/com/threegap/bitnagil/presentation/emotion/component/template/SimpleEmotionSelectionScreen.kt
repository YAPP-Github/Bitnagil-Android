package com.threegap.bitnagil.presentation.emotion.component.template

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.presentation.emotion.component.atom.EmotionMarbleImage
import com.threegap.bitnagil.presentation.emotion.model.EmotionImageUiModel
import com.threegap.bitnagil.presentation.emotion.model.EmotionScreenStep
import com.threegap.bitnagil.presentation.emotion.model.EmotionUiModel
import com.threegap.bitnagil.presentation.emotion.model.mvi.EmotionState

@Composable
fun SimpleEmotionSelectionScreen(
    state: EmotionState,
    onClickPreviousButton: () -> Unit,
    onClickEmotion: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BitnagilTheme.colors.white)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BitnagilTopBar(
            showBackButton = true,
            title = "오늘 감정 등록하기",
            onBackClick = onClickPreviousButton,
        )

        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(28.dp),
            ) {
                items(state.emotionTypeUiModels) { emotion ->
                    Column(
                        modifier = Modifier
                            .clickableWithoutRipple { onClickEmotion(emotion.emotionType) },
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        EmotionMarbleImage(
                            modifier = Modifier.aspectRatio(1f),
                            image = emotion.image,
                        )

                        Text(
                            text = emotion.emotionMarbleName,
                            style = BitnagilTheme.typography.body1Regular.copy(color = BitnagilTheme.colors.coolGray20),
                        )
                    }
                }
            }
        }
    }
}

@Preview(heightDp = 360, widthDp = 360)
@Composable
private fun Preview() {
    BitnagilTheme {
        SimpleEmotionSelectionScreen(
            state = EmotionState(
                emotionTypeUiModels = listOf(
                    EmotionUiModel(
                        emotionType = "emotionType",
                        emotionMarbleName = "name1",
                        image = EmotionImageUiModel.Url(
                            url = "https://bitnagil-s3.s3.ap-northeast-2.amazonaws.com/home_satisfaction.png",
                            offlineBackupImageResourceId = null,
                        ),
                    ),
                    EmotionUiModel(
                        emotionType = "emotionType",
                        emotionMarbleName = "name2",
                        image = EmotionImageUiModel.Url(
                            url = "https://bitnagil-s3.s3.ap-northeast-2.amazonaws.com/home_satisfaction.png",
                            offlineBackupImageResourceId = null,
                        ),
                    ),
                    EmotionUiModel(
                        emotionType = "emotionType",
                        emotionMarbleName = "name3",
                        image = EmotionImageUiModel.Url(
                            url = "https://bitnagil-s3.s3.ap-northeast-2.amazonaws.com/home_satisfaction.png",
                            offlineBackupImageResourceId = null,
                        ),
                    ),
                    EmotionUiModel(
                        emotionType = "emotionType",
                        emotionMarbleName = "name4",
                        image = EmotionImageUiModel.Url(
                            url = "https://bitnagil-s3.s3.ap-northeast-2.amazonaws.com/home_satisfaction.png",
                            offlineBackupImageResourceId = null,
                        ),
                    ),
                ),
                isLoading = false,
                step = EmotionScreenStep.Emotion,
                recommendRoutines = listOf(),
            ),
            onClickEmotion = { _ -> },
            onClickPreviousButton = {},
        )
    }
}
