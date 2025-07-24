package com.threegap.bitnagil.presentation.home.component.block

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.domain.routine.model.RoutineType
import com.threegap.bitnagil.presentation.home.model.RoutineUiModel
import com.threegap.bitnagil.presentation.home.model.SubRoutineUiModel

@Composable
fun RoutineItem(
    routine: RoutineUiModel,
    onRoutineToggle: (Boolean) -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            // todo: 리플효과 제거하기
            .clickable(
                onClick = { onRoutineToggle(!routine.isCompleted) },
            )
            .height(61.dp)
            .background(
                color = BitnagilTheme.colors.lightBlue75,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(
                start = 16.dp,
            ),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // todo: 아이콘 변경하기
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = if (routine.isCompleted) Color.Black else Color.Gray,
            )

            Text(
                text = routine.routineName,
                style = BitnagilTheme.typography.subtitle1SemiBold,
                color = BitnagilTheme.colors.navy500,
            )
        }

        // todo: 아이콘 변경하기
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = null,
            modifier = Modifier
                // todo: 리플효과 제거하기
                .clickable {
                    onMoreClick()
                }
                .padding(12.dp)
                .size(24.dp),
        )
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
            repeatDay = emptyList(),
            routineType = RoutineType.ROUTINE
        ),
        onRoutineToggle = { },
        onMoreClick = { },
    )
}
