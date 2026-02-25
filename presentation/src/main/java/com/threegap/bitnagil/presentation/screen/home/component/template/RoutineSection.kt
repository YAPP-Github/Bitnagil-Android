package com.threegap.bitnagil.presentation.screen.home.component.template

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
import com.threegap.bitnagil.domain.routine.model.DayOfWeek
import com.threegap.bitnagil.presentation.screen.home.component.block.RoutineItem
import com.threegap.bitnagil.presentation.screen.home.model.RoutineUiModel
import com.threegap.bitnagil.presentation.screen.home.util.formatExecutionTime24Hour

@Composable
fun RoutineSection(
    routine: RoutineUiModel,
    onRoutineToggle: () -> Unit,
    onSubRoutineToggle: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = routine.executionTime.formatExecutionTime24Hour(),
            style = BitnagilTheme.typography.body2Medium,
            color = BitnagilTheme.colors.coolGray10,
            modifier = Modifier.defaultMinSize(minWidth = 42.dp),
        )

        RoutineItem(
            name = routine.name,
            isCompleted = routine.isCompleted,
            subRoutineNames = routine.subRoutineNames,
            subRoutineIsCompleted = routine.subRoutineCompletionStates,
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
            id = "1",
            name = "개운하게 일어나기",
            repeatDays = listOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
            executionTime = "08:00",
            routineDate = "2023-10-27",
            isCompleted = false,
            subRoutineNames = listOf("Make bed", "Brush teeth", "Meditate"),
            subRoutineCompletionStates = listOf(true, false, false),
            recommendedRoutineType = null,
        ),
        onRoutineToggle = {},
        onSubRoutineToggle = {},
    )
}
