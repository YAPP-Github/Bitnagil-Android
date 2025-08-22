package com.threegap.bitnagil.presentation.recommendroutine.component.template

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme

@Composable
fun EmptyRecommendRoutineView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(top = 175.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "해당 난이도 루틴이 없어요",
            color = BitnagilTheme.colors.coolGray30,
            style = BitnagilTheme.typography.subtitle1SemiBold.copy(
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.None,
                ),
            ),
            modifier = Modifier.padding(bottom = 2.dp),
        )

        Text(
            text = "다른 난이도를 살펴보거나 루틴을 추가해 보세요.",
            color = BitnagilTheme.colors.coolGray70,
            style = BitnagilTheme.typography.body2Regular.copy(
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.None,
                ),
            ),
        )
    }
}

@Preview
@Composable
private fun EmptyRecommendRoutineViewPreview() {
    EmptyRecommendRoutineView()
}
