package com.threegap.bitnagil.presentation.recommendroutine.component.block

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple

@Composable
fun EmotionRecommendRoutineButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BitnagilTheme.colors.coolGray10,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(
                vertical = 14.dp,
                horizontal = 16.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.default_marble),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 10.dp)
                .size(24.dp),
        )

        Text(
            text = "내 기분에 맞는 루틴 추천받기",
            color = BitnagilTheme.colors.white,
            style = BitnagilTheme.typography.body2SemiBold,
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .background(
                    color = BitnagilTheme.colors.orange500,
                    shape = RoundedCornerShape(8.dp),
                )
                .height(38.dp)
                .clickableWithoutRipple { onClick() }
                .padding(vertical = 10.dp, horizontal = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "추천받기",
                color = BitnagilTheme.colors.white,
                style = BitnagilTheme.typography.caption1SemiBold,
            )
        }
    }
}

@Preview
@Composable
private fun EmotionRecommendRoutineButtonPreview() {
    EmotionRecommendRoutineButton(
        onClick = {},
    )
}
