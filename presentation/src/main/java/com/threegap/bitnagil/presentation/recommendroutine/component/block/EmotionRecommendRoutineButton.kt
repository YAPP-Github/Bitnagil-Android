package com.threegap.bitnagil.presentation.recommendroutine.component.block

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme

@Composable
fun EmotionRecommendRoutineButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
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
            // todo: 리플효과 제거하기
            .clickable { onClick() }
            .padding(
                vertical = 16.dp,
                horizontal = 24.dp,
            ),
    ) {
        // todo: 아이콘 변경하기
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 10.dp)
                .size(20.dp),
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
