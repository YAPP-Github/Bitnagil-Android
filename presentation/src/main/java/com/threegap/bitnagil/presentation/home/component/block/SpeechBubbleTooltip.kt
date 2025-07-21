package com.threegap.bitnagil.presentation.home.component.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.presentation.home.component.atom.roundedTriangleShape

@Composable
fun SpeechBubbleTooltip(
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = BitnagilTheme.colors.navy400,
                    shape = RoundedCornerShape(8.dp),
                )
                .padding(10.dp),
        ) {
            Text(
                text = text,
                color = BitnagilTheme.colors.white,
                style = BitnagilTheme.typography.caption1Medium,
            )
        }

        Box(
            modifier = Modifier
                .size(18.dp, 10.dp)
                .background(
                    color = BitnagilTheme.colors.navy400,
                    shape = roundedTriangleShape(6f),
                ),
        )
    }
}

@Preview
@Composable
private fun SpeechBubbleTooltipPreview() {
    SpeechBubbleTooltip(
        text = "감정 기록 시, 루틴을 추천 받아요!",
    )
}
