package com.threegap.bitnagil.presentation.home.component.template

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.domain.routine.model.DayOfWeek
import com.threegap.bitnagil.domain.routine.model.DayOfWeek.Companion.formatRepeatDays
import com.threegap.bitnagil.domain.routine.model.RoutineType
import com.threegap.bitnagil.presentation.home.model.RoutineUiModel
import com.threegap.bitnagil.presentation.home.model.SubRoutineUiModel
import com.threegap.bitnagil.presentation.home.util.formatExecutionTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineDetailsBottomSheet(
    routine: RoutineUiModel,
    onDismiss: () -> Unit,
    onEdit: (String) -> Unit,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = BitnagilTheme.colors.white,
        contentColor = BitnagilTheme.colors.white,
        modifier = modifier,
    ) {
        RoutineInfoContent(
            routine = routine,
            onEdit = { onEdit(routine.routineId) },
            onDelete = { onDelete(routine.routineId) },
            modifier = Modifier.padding(horizontal = 20.dp),
        )
    }
}

@Composable
private fun RoutineInfoContent(
    routine: RoutineUiModel,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    BitnagilIcon(
                        id = R.drawable.ic_name_routine,
                        tint = BitnagilTheme.colors.coolGray50,
                    )

                    Text(
                        text = "루틴 이름",
                        color = BitnagilTheme.colors.coolGray50,
                        style = BitnagilTheme.typography.body2Medium,
                    )
                }
                Text(
                    text = routine.routineName,
                    color = BitnagilTheme.colors.coolGray10,
                    style = BitnagilTheme.typography.body2SemiBold,
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.align(Alignment.TopStart),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    BitnagilIcon(
                        id = R.drawable.ic_detail_routine,
                        tint = BitnagilTheme.colors.coolGray50,
                    )

                    Text(
                        text = "세부 루틴",
                        color = BitnagilTheme.colors.coolGray50,
                        style = BitnagilTheme.typography.body2Medium,
                    )
                }

                if (routine.subRoutines.isEmpty()) {
                    Text(
                        text = "세부루틴 없음",
                        color = BitnagilTheme.colors.coolGray10,
                        style = BitnagilTheme.typography.body2SemiBold,
                        modifier = Modifier.align(Alignment.CenterEnd),
                    )
                } else {
                    Column(
                        modifier = Modifier.align(Alignment.TopEnd),
                    ) {
                        routine.subRoutines.forEach { subRoutine ->
                            Text(
                                text = subRoutine.subRoutineName,
                                color = BitnagilTheme.colors.coolGray10,
                                style = BitnagilTheme.typography.body2SemiBold,
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                            )
                        }
                    }
                }
            }

            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        BitnagilIcon(
                            id = R.drawable.ic_rotate,
                            tint = BitnagilTheme.colors.coolGray50,
                        )

                        Text(
                            text = "루틴 반복",
                            color = BitnagilTheme.colors.coolGray50,
                            style = BitnagilTheme.typography.body2Medium,
                        )
                    }

                    Text(
                        text = routine.repeatDay.formatRepeatDays(),
                        color = BitnagilTheme.colors.coolGray10,
                        style = BitnagilTheme.typography.body2SemiBold,
                    )
                }
                Text(
                    text = "${routine.executionTime.formatExecutionTime()} 시작",
                    color = BitnagilTheme.colors.coolGray40,
                    style = BitnagilTheme.typography.caption1Medium,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        Spacer(modifier = Modifier.height(33.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(13.dp),
            modifier = Modifier
                .padding(vertical = 14.dp)
                .height(54.dp)
                .fillMaxWidth(),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(
                        color = BitnagilTheme.colors.navy500,
                        shape = RoundedCornerShape(12.dp),
                    )
                    .clickableWithoutRipple { onEdit() },
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    BitnagilIcon(
                        id = com.threegap.bitnagil.designsystem.R.drawable.ic_edit,
                        tint = BitnagilTheme.colors.white,
                    )
                    Text(
                        text = "수정하기",
                        color = BitnagilTheme.colors.white,
                        style = BitnagilTheme.typography.subtitle1SemiBold,
                    )
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .border(
                        width = 1.dp,
                        color = BitnagilTheme.colors.navy500,
                        shape = RoundedCornerShape(12.dp),
                    )
                    .background(
                        color = BitnagilTheme.colors.white,
                        shape = RoundedCornerShape(12.dp),
                    )
                    .clickableWithoutRipple { onDelete() },
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    BitnagilIcon(
                        id = com.threegap.bitnagil.designsystem.R.drawable.ic_trash,
                        tint = BitnagilTheme.colors.navy500,
                    )
                    Text(
                        text = "삭제하기",
                        color = BitnagilTheme.colors.navy500,
                        style = BitnagilTheme.typography.subtitle1SemiBold,
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun RoutineInfoContentPreview() {
    RoutineInfoContent(
        routine = RoutineUiModel(
            routineId = "uuid1",
            historySeq = 1,
            repeatDay = listOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
            routineName = "개운하게 일어나기",
            executionTime = "20:30:00",
            isCompleted = false,
            isModified = false,
            subRoutines = listOf(
                SubRoutineUiModel(
                    subRoutineId = "uuid1",
                    historySeq = 1,
                    subRoutineName = "물 마시기",
                    sortOrder = 1,
                    isCompleted = false,
                    isModified = false,
                    routineType = RoutineType.SUB_ROUTINE,
                ),
                SubRoutineUiModel(
                    subRoutineId = "uuid2",
                    historySeq = 1,
                    subRoutineName = "스트레칭하기",
                    sortOrder = 1,
                    isCompleted = false,
                    isModified = false,
                    routineType = RoutineType.SUB_ROUTINE,
                ),
                SubRoutineUiModel(
                    subRoutineId = "uuid3",
                    historySeq = 1,
                    subRoutineName = "심호흡하기",
                    sortOrder = 1,
                    isCompleted = false,
                    isModified = false,
                    routineType = RoutineType.SUB_ROUTINE,
                ),
            ),
            routineType = RoutineType.ROUTINE,
        ),
        onEdit = {},
        onDelete = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun RoutineInfoContentSinglePreview() {
    RoutineInfoContent(
        routine = RoutineUiModel(
            routineId = "uuid1",
            historySeq = 1,
            routineName = "개운하게 일어나기",
            executionTime = "20:30:00",
            isCompleted = false,
            isModified = false,
            subRoutines = emptyList(),
            repeatDay = emptyList(),
            routineType = RoutineType.ROUTINE,
        ),
        onEdit = {},
        onDelete = {},
    )
}
