package com.threegap.bitnagil.presentation.home.component.template

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
fun RoutineEmptyView(
    onRegisterRoutineClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            text = "등록한 루틴이 없어요",
            style = BitnagilTheme.typography.subtitle1SemiBold,
            color = BitnagilTheme.colors.coolGray30,
        )
        Text(
            text = "루틴을 등록해서 빛나길을 시작해보세요",
            style = BitnagilTheme.typography.body2Medium,
            color = BitnagilTheme.colors.coolGray70,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .background(
                    color = BitnagilTheme.colors.navy50,
                    shape = RoundedCornerShape(100.dp),
                )
                // todo: 리플효과 제거하기
                .clickable {
                    onRegisterRoutineClick()
                }
                .padding(
                    vertical = 8.dp,
                    horizontal = 10.dp,
                ),
        ) {
            Text(
                text = "루틴 등록하기",
                style = BitnagilTheme.typography.caption1Medium,
                color = BitnagilTheme.colors.coolGray30,
            )
        }
    }
}

@Preview
@Composable
private fun RoutineEmptyViewPreview() {
    RoutineEmptyView(
        onRegisterRoutineClick = {},
    )
}
