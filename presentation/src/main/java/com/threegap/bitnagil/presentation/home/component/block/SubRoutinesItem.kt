package com.threegap.bitnagil.presentation.home.component.block

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import com.threegap.bitnagil.presentation.home.model.SubRoutineUiModel

@Composable
fun SubRoutinesItem(
    subRoutines: List<SubRoutineUiModel>,
    onSubRoutineToggle: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        subRoutines.forEach { subRoutine ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableWithoutRipple {
                        onSubRoutineToggle(subRoutine.subRoutineId, !subRoutine.isCompleted)
                    },
            ) {
                BitnagilIcon(
                    id = if (subRoutine.isCompleted) R.drawable.ic_check_circle else R.drawable.ic_check_default,
                    tint = null,
                    modifier = Modifier.size(24.dp),
                )

                Text(
                    text = subRoutine.subRoutineName,
                    style = BitnagilTheme.typography.body2Medium,
                    color = BitnagilTheme.colors.coolGray40,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SubRoutinesItemPreview() {
    SubRoutinesItem(
        subRoutines = listOf(
            SubRoutineUiModel(
                subRoutineId = "uuid1",
                historySeq = 1,
                subRoutineName = "물 마시기",
                sortOrder = 1,
                routineCompletionId = 1,
                isCompleted = true,
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
        onSubRoutineToggle = { _, _ -> },
    )
}
