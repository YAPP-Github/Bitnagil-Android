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
import com.threegap.bitnagil.domain.routine.model.RoutineType
import com.threegap.bitnagil.presentation.home.model.RoutineUiModel
import com.threegap.bitnagil.presentation.home.model.SubRoutineUiModel

@Composable
fun RoutineItem(
    routine: RoutineUiModel,
    onRoutineToggle: (Boolean) -> Unit,
    onSubRoutineToggle: (String, Boolean) -> Unit,
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
                .clickableWithoutRipple { onRoutineToggle(!routine.isCompleted) },
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
                id = if (routine.isCompleted) R.drawable.ic_check_circle else R.drawable.ic_check_default,
                tint = null,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(28.dp),
            )
        }

        if (routine.subRoutines.isNotEmpty()) {
            HorizontalDivider(
                thickness = 1.dp,
                color = BitnagilTheme.colors.coolGray97,
                modifier = Modifier.padding(vertical = 10.dp),
            )

            SubRoutinesItem(
                subRoutines = routine.subRoutines,
                onSubRoutineToggle = { subRoutineId, isCompleted ->
                    onSubRoutineToggle(subRoutineId, isCompleted)
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
            historySeq = 1,
            routineName = "개운하게 일어나기",
            executionTime = "20:30:00",
            isCompleted = false,
            routineCompletionId = 1,
            isModified = false,
            subRoutines = listOf(
                SubRoutineUiModel(
                    subRoutineId = "uuid1",
                    historySeq = 1,
                    subRoutineName = "물 마시기",
                    sortOrder = 1,
                    routineCompletionId = 1,
                    isCompleted = false,
                    isModified = false,
                    routineType = RoutineType.SUB_ROUTINE,
                ),
                SubRoutineUiModel(
                    subRoutineId = "uuid2",
                    historySeq = 1,
                    subRoutineName = "스트레칭하기",
                    sortOrder = 1,
                    routineCompletionId = 1,
                    isCompleted = false,
                    isModified = false,
                    routineType = RoutineType.SUB_ROUTINE,
                ),
                SubRoutineUiModel(
                    subRoutineId = "uuid3",
                    historySeq = 1,
                    subRoutineName = "심호흡하기",
                    sortOrder = 1,
                    routineCompletionId = 1,
                    isCompleted = false,
                    isModified = false,
                    routineType = RoutineType.SUB_ROUTINE,
                ),
            ),
            repeatDay = emptyList(),
            routineType = RoutineType.ROUTINE,
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
            executionTime = "20:30:00",
            routineCompletionId = 1,
            isCompleted = false,
            isModified = false,
            subRoutines = emptyList(),
            historySeq = 1,
            repeatDay = listOf(),
            routineType = RoutineType.ROUTINE,
        ),
        onRoutineToggle = {},
        onSubRoutineToggle = { _, _ -> },
    )
}
