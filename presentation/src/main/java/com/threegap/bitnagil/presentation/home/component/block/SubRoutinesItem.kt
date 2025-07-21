package com.threegap.bitnagil.presentation.home.component.block

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.presentation.home.model.SubRoutineUiModel

@Composable
fun SubRoutinesItem(
    subRoutines: List<SubRoutineUiModel>,
    onSubRoutineToggle: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "세부 루틴",
            style = BitnagilTheme.typography.caption1Medium,
            color = BitnagilTheme.colors.coolGray60,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        subRoutines.forEach { subRoutine ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(11.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    // todo: 리플효과 제거하기
                    .clickable {
                        onSubRoutineToggle(subRoutine.subRoutineId, !subRoutine.isCompleted)
                    }
                    .padding(
                        vertical = 7.dp,
                        horizontal = 14.dp,
                    ),
            ) {
                // todo: 아이콘 변경하기
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = if (subRoutine.isCompleted) {
                        BitnagilTheme.colors.orange500
                    } else {
                        BitnagilTheme.colors.coolGray96
                    },
                )

                Text(
                    text = subRoutine.subRoutineName,
                    style = BitnagilTheme.typography.body2Medium,
                    color = BitnagilTheme.colors.coolGray20,
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
            SubRoutineUiModel(subRoutineId = "uuid1", subRoutineName = "물 마시기", sortOrder = 1),
            SubRoutineUiModel(subRoutineId = "uuid2", subRoutineName = "스트레칭하기", sortOrder = 2),
            SubRoutineUiModel(subRoutineId = "uuid3", subRoutineName = "심호흡하기", sortOrder = 3),
        ),
        onSubRoutineToggle = { _, _ -> },
    )
}
