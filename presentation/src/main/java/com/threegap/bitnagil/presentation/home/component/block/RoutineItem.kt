package com.threegap.bitnagil.presentation.home.component.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.threegap.bitnagil.presentation.home.model.RoutineUiModel

@Composable
fun RoutineItem(
    routine: RoutineUiModel,
    onRoutineToggle: (Boolean) -> Unit,
    onSubRoutineToggle: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BitnagilTheme.colors.white,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(
                vertical = 14.dp,
                horizontal = 16.dp,
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickableWithoutRipple { onRoutineToggle(!routine.routineCompleteYn) },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = routine.routineName,
                style = BitnagilTheme.typography.body1SemiBold,
                color = BitnagilTheme.colors.coolGray10,
                modifier = Modifier.weight(1f),
            )

            BitnagilIcon(
                id = if (routine.routineCompleteYn) R.drawable.ic_check_circle else R.drawable.ic_check_default,
                tint = null,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(28.dp),
            )
        }

        if (routine.subRoutineNames.isNotEmpty()) {
            HorizontalDivider(
                thickness = 1.dp,
                color = BitnagilTheme.colors.coolGray97,
                modifier = Modifier.padding(vertical = 10.dp),
            )

            SubRoutinesItem(
                subRoutineNames = routine.subRoutineNames,
                subRoutineCompleteYn = routine.subRoutineCompleteYn,
                onSubRoutineToggle = { index, isCompleted ->
                    onSubRoutineToggle(index, isCompleted)
                },
            )
        }
    }
}

@Preview
@Composable
private fun RoutineItemPreview() {
    RoutineItem(
        routine = RoutineUiModel(
            routineId = "uuid1",
            routineName = "개운하게 일어나기",
            repeatDay = emptyList(),
            executionTime = "20:30:00",
            routineDate = "2025-08-15",
            routineCompleteYn = false,
            subRoutineNames = listOf("물 마시기", "스트레칭하기", "심호흡하기"),
            subRoutineCompleteYn = listOf(true, false, true),
            recommendedRoutineType = null,
        ),
        onRoutineToggle = { },
        onSubRoutineToggle = { _, _ -> },
    )
}

@Preview
@Composable
private fun NoneSubRoutineRoutineItemPreview() {
    RoutineItem(
        routine = RoutineUiModel(
            routineId = "uuid1",
            routineName = "개운하게 일어나기",
            repeatDay = emptyList(),
            executionTime = "20:30:00",
            routineDate = "2025-08-15",
            routineCompleteYn = false,
            subRoutineNames = emptyList(),
            subRoutineCompleteYn = emptyList(),
            recommendedRoutineType = null,
        ),
        onRoutineToggle = {},
        onSubRoutineToggle = { _, _ -> },
    )
}
