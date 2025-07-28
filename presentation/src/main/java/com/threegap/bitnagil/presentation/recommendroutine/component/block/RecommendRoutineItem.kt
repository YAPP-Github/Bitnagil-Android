package com.threegap.bitnagil.presentation.recommendroutine.component.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple

@Composable
fun RecommendRoutineItem(
    routineName: String,
    routineDescription: String,
    onAddRoutineClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(
                color = BitnagilTheme.colors.lightBlue75,
                shape = RoundedCornerShape(12.dp),
            )
            .clickableWithoutRipple(onClick = onAddRoutineClick)
            .padding(
                vertical = 16.dp,
                horizontal = 20.dp,
            ),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = routineName,
                color = BitnagilTheme.colors.navy500,
                style = BitnagilTheme.typography.body1SemiBold,
            )

            Text(
                text = routineDescription,
                color = BitnagilTheme.colors.navy300,
                style = BitnagilTheme.typography.body2Regular,
            )
        }

        BitnagilIcon(
            id = R.drawable.ic_plus,
            tint = BitnagilTheme.colors.navy400,
            modifier = Modifier.size(32.dp),
        )
    }
}

@Preview
@Composable
private fun RecommendRoutineItemPreview() {
    RecommendRoutineItem(
        routineName = "쓰레기 버리러 나가기",
        routineDescription = "간단한 외출도 의미있는 변화예요.",
        onAddRoutineClick = {},
    )
}
