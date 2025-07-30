package com.threegap.bitnagil.presentation.home.component.template

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.domain.routine.model.RoutineType
import com.threegap.bitnagil.presentation.home.component.block.RoutineItem
import com.threegap.bitnagil.presentation.home.component.block.SubRoutinesItem
import com.threegap.bitnagil.presentation.home.model.RoutineUiModel
import com.threegap.bitnagil.presentation.home.model.SubRoutineUiModel
import com.threegap.bitnagil.presentation.home.util.formatExecutionTime

@Composable
fun RoutineSection(
    routine: RoutineUiModel,
    onRoutineToggle: (Boolean) -> Unit,
    onSubRoutineToggle: (String, Boolean) -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "${routine.executionTime.formatExecutionTime()}부터 시작",
            style = BitnagilTheme.typography.caption1Regular,
            color = BitnagilTheme.colors.navy300,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        start = 9.dp,
                        end = 10.dp,
                    ),
            ) {
                VerticalDivider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .border(
                            width = 1.dp,
                            color = BitnagilTheme.colors.lightBlue300,
                        )
                        .align(Alignment.Center),
                )

                Box(
                    modifier = Modifier
                        .padding(top = 26.5.dp)
                        .size(8.dp)
                        .background(
                            color = BitnagilTheme.colors.navy500,
                            shape = CircleShape,
                        ),
                )
            }

            Column {
                RoutineItem(
                    routine = routine,
                    onRoutineToggle = onRoutineToggle,
                    onMoreClick = onMoreClick,
                )

                if (routine.subRoutines.isNotEmpty()) {
                    SubRoutinesItem(
                        subRoutines = routine.subRoutines,
                        onSubRoutineToggle = { subRoutineId, isCompleted ->
                            onSubRoutineToggle(subRoutineId, isCompleted)
                        },
                        modifier = Modifier.padding(top = 10.dp),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RoutineTemplatePreview() {
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
        onMoreClick = {},
    )
}
