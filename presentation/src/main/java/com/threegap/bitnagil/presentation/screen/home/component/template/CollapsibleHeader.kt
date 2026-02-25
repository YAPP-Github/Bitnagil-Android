package com.threegap.bitnagil.presentation.screen.home.component.template

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.presentation.screen.home.component.atom.EmotionRegisterButton
import com.threegap.bitnagil.presentation.screen.home.model.DailyEmotionUiModel

@Composable
fun CollapsibleHeader(
    modifier: Modifier = Modifier,
    welcomeMessage: String,
    dailyEmotion: DailyEmotionUiModel,
    onRegisterEmotionClick: () -> Unit,
) {
    val baseImageHeight = 148.dp
    val baseImageWidth = 108.dp

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.align(Alignment.TopStart),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = welcomeMessage,
                style = BitnagilTheme.typography.cafe24SsurroundAir.copy(
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.None,
                    ),
                ),
                color = BitnagilTheme.colors.white,
                fontWeight = FontWeight.SemiBold,
            )

            EmotionRegisterButton(
                onClick = onRegisterEmotionClick,
                enabled = !dailyEmotion.hasEmotion,
            )
        }

        AsyncImage(
            model = dailyEmotion.imageUrl,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(baseImageWidth, baseImageHeight),
            contentDescription = null,
            placeholder = painterResource(R.drawable.default_emotion),
            error = painterResource(R.drawable.default_emotion),
        )
    }
}

@Preview
@Composable
private fun CollapsibleHeaderPreview() {
    CollapsibleHeader(
        modifier = Modifier.fillMaxWidth(),
        welcomeMessage = "대현님 오셨군요!\n오늘 기분은 어떤가요?!",
        dailyEmotion = DailyEmotionUiModel.Companion.INIT,
        onRegisterEmotionClick = {},
    )
}
