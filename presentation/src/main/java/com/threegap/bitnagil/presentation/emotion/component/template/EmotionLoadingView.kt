package com.threegap.bitnagil.presentation.emotion.component.template

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.presentation.common.dimension.pxToDp
import com.threegap.bitnagil.presentation.emotion.component.atom.EmotionMarbleImage
import com.threegap.bitnagil.presentation.emotion.component.atom.SpeechBubbleText
import com.threegap.bitnagil.presentation.emotion.model.EmotionUiModel

@Composable
fun EmotionLoadingView(
    emotion: EmotionUiModel,
) {
    Box(
        modifier = Modifier.fillMaxSize().background(BitnagilTheme.colors.white),
    ) {
        BackgroundBox(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SpeechBubbleText(
                text = getRecommendRoutineText(emotion),
                backgroundColor = 0xFF000000,
                textColor = 0xFFFFFFFF,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(280.dp)
                    .offset(y = 16.dp)
                    .zIndex(1f),
            ) {
                Image(
                    painter = painterResource(R.drawable.img_marble_pomo),
                    contentDescription = null,
                )

                Image(
                    modifier = Modifier
                        .offset(y = 117.dp, x = 18.dp)
                        .zIndex(2f),
                    painter = painterResource(R.drawable.img_marble_pomo_left_hand),
                    contentDescription = null,
                )

                Image(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(y = 117.dp, x = (-17).dp)
                        .zIndex(2f),
                    painter = painterResource(R.drawable.img_marble_pomo_right_hand),
                    contentDescription = null,
                )

                EmotionMarbleImage(
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.TopCenter)
                        .offset(y = 97.dp)
                        .zIndex(1f),
                    image = emotion.image,
                )
            }

            Image(
                modifier = Modifier.height(150.dp).fillMaxWidth(),
                painter = painterResource(R.drawable.img_ground),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )
        }
    }
}

@Composable
private fun BackgroundBox(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier) {
        val height = constraints.maxHeight
        val width = constraints.maxWidth

        BackgroundIcon(
            iconResourceId = R.drawable.ic_wakeup,
            offsetX = (width * 0.03f).pxToDp(),
            offsetY = (height * 0.03f).pxToDp(),
        )

        BackgroundIcon(
            iconResourceId = R.drawable.ic_rest,
            offsetX = (width * 0.42f).pxToDp(),
            offsetY = (height * 0.08f).pxToDp(),
        )

        BackgroundIcon(
            iconResourceId = R.drawable.ic_shine,
            offsetX = (width * 0.87f).pxToDp(),
            offsetY = (height * 0.05f).pxToDp(),
        )

        BackgroundIcon(
            iconResourceId = R.drawable.ic_grow,
            offsetX = (width * -0.05f).pxToDp(),
            offsetY = (height * 0.27f).pxToDp(),
        )

        BackgroundIcon(
            iconResourceId = R.drawable.img_start_time,
            offsetX = (width * 0.66f).pxToDp(),
            offsetY = (height * 0.25f).pxToDp(),
        )

        BackgroundIcon(
            iconResourceId = R.drawable.ic_outside,
            offsetX = (width * -0.14f).pxToDp(),
            offsetY = (height * 0.63f).pxToDp(),
        )

        BackgroundIcon(
            iconResourceId = R.drawable.ic_connect,
            offsetX = (width * 0.20f).pxToDp(),
            offsetY = (height * 0.46f).pxToDp(),
        )

        BackgroundIcon(
            iconResourceId = R.drawable.img_routine_period,
            offsetX = (width * 0.93f).pxToDp(),
            offsetY = (height * 0.54f).pxToDp(),
        )
    }
}

@Composable
private fun BackgroundIcon(iconResourceId: Int, offsetX: Dp, offsetY: Dp) {
    Image(
        modifier = Modifier.size(72.dp).offset(x = offsetX, y = offsetY),
        painter = painterResource(iconResourceId),
        colorFilter = ColorFilter.tint(BitnagilTheme.colors.coolGray98),
        contentDescription = null,
    )
}

private fun getRecommendRoutineText(emotion: EmotionUiModel): String {
    return when (emotion.emotionType) {
        "CALM" -> "평온한 하루에 맞춰\n루틴을 추천해드릴게요!"
        "VITALITY" -> "활기찬 하루에 맞춰\n루틴을 추천해드릴게요!"
        "LETHARGY" -> "무기력한 하루에 맞춰\n루틴을 추천해드릴게요!"
        "ANXIETY" -> "불안한 하루에 맞춰\n루틴을 추천해드릴게요!"
        "SATISFACTION" -> "만족스러운 하루에 맞춰\n루틴을 추천해드릴게요!"
        "FATIGUE" -> "피곤한 하루에 맞춰\n루틴을 추천해드릴게요!"
        else -> "감정에 맞춰\n루틴을 추천해드릴게요!"
    }
}

@Preview
@Composable
private fun EmotionLoadingViewPreview() {
    BitnagilTheme {
        EmotionLoadingView(
            emotion = EmotionUiModel.Default,
        )
    }
}
