package com.threegap.bitnagil.presentation.recommendroutine.component.template

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme

@Composable
fun RecommendRoutineEmptyView(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            text = "맞춤 추천 루틴이 없어요",
            color = BitnagilTheme.colors.coolGray30,
            style = BitnagilTheme.typography.subtitle1SemiBold,
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "루틴을 등록해서 나만의 추천 루틴을 받아보세요!",
            color = BitnagilTheme.colors.coolGray70,
            style = BitnagilTheme.typography.body2Medium,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(
                    color = BitnagilTheme.colors.navy50,
                    shape = RoundedCornerShape(100.dp),
                )
                // todo: 리플효과 제거하기
                .clickable { onClick() }
                .padding(
                    vertical = 8.dp,
                    horizontal = 10.dp,
                ),
        ) {
            Text(
                text = "루틴 등록하기",
                color = BitnagilTheme.colors.coolGray30,
                style = BitnagilTheme.typography.caption1Medium,
            )
        }
    }
}

@Preview
@Composable
private fun RecommendRoutineEmptyViewPreview() {
    RecommendRoutineEmptyView(
        onClick = {},
    )
}
