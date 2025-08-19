package com.threegap.bitnagil.presentation.recommendroutine.component.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
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
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendLevel
import com.threegap.bitnagil.presentation.recommendroutine.model.RecommendRoutineUiModel
import com.threegap.bitnagil.presentation.recommendroutine.model.RecommendSubRoutineUiModel

@Composable
fun RecommendRoutineItem(
    routine: RecommendRoutineUiModel,
    onAddRoutineClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = BitnagilTheme.colors.white,
                shape = RoundedCornerShape(12.dp),
            )
            .clickableWithoutRipple(onClick = onAddRoutineClick)
            .fillMaxWidth()
            .padding(vertical = 14.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 11.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BitnagilIcon(
                id = routine.icon,
                tint = null,
                modifier = Modifier
                    .background(
                        color = routine.color,
                        shape = RoundedCornerShape(4.dp),
                    )
                    .padding(4.dp),
            )

            Text(
                text = routine.name,
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.body1SemiBold,
                modifier = Modifier.padding(start = 10.dp),
            )

            Spacer(modifier = Modifier.weight(1f))

            BitnagilIcon(
                id = R.drawable.ic_add,
                tint = BitnagilTheme.colors.coolGray10,
                modifier = Modifier.size(24.dp),
            )
        }

        if (routine.recommendSubRoutines.isNotEmpty()) {
            HorizontalDivider(
                thickness = 1.dp,
                color = BitnagilTheme.colors.coolGray97,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = "세부 루틴",
                    color = BitnagilTheme.colors.coolGray40,
                    style = BitnagilTheme.typography.body2Medium,
                )

                routine.recommendSubRoutines.forEach { subRoutine ->
                    Text(
                        text = "•  ${subRoutine.name}",
                        color = BitnagilTheme.colors.coolGray40,
                        style = BitnagilTheme.typography.body2Medium,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RecommendRoutineItemPreview() {
    RecommendRoutineItem(
        routine = RecommendRoutineUiModel(
            id = 1,
            name = "개운하게 일어나기",
            level = RecommendLevel.LEVEL1,
            recommendedRoutineType = RecommendCategory.WAKE_UP,
            recommendSubRoutines = listOf(
                RecommendSubRoutineUiModel(
                    id = 1,
                    name = "일어나기",
                ),
            ),
        ),
        onAddRoutineClick = {},
    )
}

@Preview
@Composable
private fun EmptySubRoutineRecommendRoutineItemPreview() {
    RecommendRoutineItem(
        routine = RecommendRoutineUiModel(
            id = 1,
            name = "개운하게 일어나기",
            level = RecommendLevel.LEVEL1,
            recommendedRoutineType = RecommendCategory.WAKE_UP,
            recommendSubRoutines = emptyList(),
        ),
        onAddRoutineClick = {},
    )
}
