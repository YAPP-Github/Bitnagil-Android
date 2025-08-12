package com.threegap.bitnagil.presentation.home.component.template

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.domain.routine.model.RoutineType
import com.threegap.bitnagil.presentation.home.component.block.RoutineItem
import com.threegap.bitnagil.presentation.home.model.RoutineUiModel
import com.threegap.bitnagil.presentation.home.model.SubRoutineUiModel
import com.threegap.bitnagil.presentation.home.util.formatExecutionTime

@Composable
fun RoutineSection(
    routine: RoutineUiModel,
    onRoutineToggle: (Boolean) -> Unit,
    onSubRoutineToggle: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = routine.executionTime.formatExecutionTime(),
            style = BitnagilTheme.typography.body2Medium,
            color = BitnagilTheme.colors.coolGray10,
            modifier = Modifier.defaultMinSize(minWidth = 42.dp),
        )

        RoutineItem(
            routine = routine,
            onRoutineToggle = onRoutineToggle,
            onSubRoutineToggle = onSubRoutineToggle,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RoutineSectionPreview() {
    RoutineSection(
        routine = RoutineUiModel(
            routineId = "uuid1",
            routineName = "개운하게 일어나기",
            executionTime = "20:30:00",
            routineCompletionId = 1,
            isCompleted = false,
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
            historySeq = 1,
            repeatDay = listOf(),
            routineType = RoutineType.ROUTINE,
        ),
        onRoutineToggle = {},
        onSubRoutineToggle = { _, _ -> },
    )
}
