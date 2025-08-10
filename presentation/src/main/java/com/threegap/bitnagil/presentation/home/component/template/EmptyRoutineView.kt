package com.threegap.bitnagil.presentation.home.component.template

import androidx.compose.foundation.background
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
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple

@Composable
fun EmptyRoutineView(
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
            modifier = Modifier.height(28.dp),
        )
        Text(
            text = "루틴을 등록하고, 작은 변화부터 시작해보세요!",
            style = BitnagilTheme.typography.body2Regular,
            color = BitnagilTheme.colors.coolGray70,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .background(
                    color = BitnagilTheme.colors.coolGray96,
                    shape = RoundedCornerShape(8.dp),
                )
                .clickableWithoutRipple { onRegisterRoutineClick() }
                .padding(
                    vertical = 10.dp,
                    horizontal = 14.dp,
                ),
        ) {
            Text(
                text = "루틴 등록하기",
                style = BitnagilTheme.typography.caption1SemiBold,
                color = BitnagilTheme.colors.coolGray30,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RoutineEmptyViewPreview() {
    EmptyRoutineView(
        onRegisterRoutineClick = {},
    )
}
