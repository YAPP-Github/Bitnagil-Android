package com.threegap.bitnagil.presentation.recommendroutine.component.block

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple

@Composable
fun EmotionRecommendRoutineButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = BitnagilTheme.colors.navy100,
                shape = RoundedCornerShape(12.dp),
            )
            .background(
                color = BitnagilTheme.colors.white,
                shape = RoundedCornerShape(12.dp),
            )
            .clickableWithoutRipple { onClick() }
            .padding(
                vertical = 16.dp,
                horizontal = 24.dp,
            ),
    ) {
        BitnagilIcon(
            id = R.drawable.ic_plus,
            tint = BitnagilTheme.colors.navy400,
            modifier = Modifier
                .padding(end = 10.dp)
                .size(20.dp)
        )

        Text(
            text = "오늘의 감정 루틴 추천 받기",
            color = BitnagilTheme.colors.navy400,
            style = BitnagilTheme.typography.body2Medium,
        )
    }
}

@Preview
@Composable
private fun EmotionRecommendRoutineButtonPreview() {
    EmotionRecommendRoutineButton(
        onClick = {},
    )
}
