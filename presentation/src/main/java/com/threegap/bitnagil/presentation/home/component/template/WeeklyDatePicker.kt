package com.threegap.bitnagil.presentation.home.component.template

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIconButton
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.presentation.home.model.RoutinesUiModel
import com.threegap.bitnagil.presentation.home.util.formatDayOfMonth
import com.threegap.bitnagil.presentation.home.util.formatDayOfWeekShort
import com.threegap.bitnagil.presentation.home.util.formatMonthYear
import com.threegap.bitnagil.presentation.home.util.getCurrentWeekDays
import java.time.LocalDate

@Composable
fun WeeklyDatePicker(
    selectedDate: LocalDate,
    weeklyDates: List<LocalDate>,
    routines: RoutinesUiModel,
    onDateSelect: (LocalDate) -> Unit,
    onPreviousWeekClick: () -> Unit,
    onNextWeekClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val today = remember { LocalDate.now() }
    val completionStates = remember(weeklyDates, routines) {
        weeklyDates.associateWith { date ->
            routines.routines[date.toString()]?.allCompleted ?: false
        }
    }

    Column(
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 4.dp,
                    start = 16.dp,
                ),
        ) {
            Text(
                text = selectedDate.formatMonthYear(),
                style = BitnagilTheme.typography.title3SemiBold,
                color = BitnagilTheme.colors.coolGray10,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BitnagilIconButton(
                    id = R.drawable.ic_chevron_left_md,
                    onClick = onPreviousWeekClick,
                    paddingValues = PaddingValues(12.dp),
                    tint = BitnagilTheme.colors.coolGray10,
                )

                BitnagilIconButton(
                    id = R.drawable.ic_chevron_right_md,
                    onClick = onNextWeekClick,
                    paddingValues = PaddingValues(12.dp),
                    tint = BitnagilTheme.colors.coolGray10,
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp,
                ),
        ) {
            weeklyDates.forEach { date ->
                DateItem(
                    date = date,
                    isSelected = selectedDate == date,
                    isToday = date == today,
                    isCompleted = completionStates[date] ?: false,
                    onDateClick = { onDateSelect(date) },
                )
            }
        }
    }
}

@Composable
private fun DateItem(
    date: LocalDate,
    isSelected: Boolean,
    isToday: Boolean,
    isCompleted: Boolean,
    onDateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(7.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickableWithoutRipple { onDateClick() },
    ) {
        Text(
            text = if (!isToday) date.formatDayOfWeekShort() else "오늘",
            style = BitnagilTheme.typography.caption1SemiBold,
            color = if (!isSelected) BitnagilTheme.colors.coolGray70 else BitnagilTheme.colors.coolGray10,
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .size(30.dp)
                .background(
                    color = if (!isSelected) Color.Transparent else BitnagilTheme.colors.coolGray10,
                    shape = RoundedCornerShape(8.dp),
                ),
        ) {
            Text(
                text = date.formatDayOfMonth(),
                style = if (!isSelected) BitnagilTheme.typography.body2Medium else BitnagilTheme.typography.body2SemiBold,
                color = if (!isSelected) BitnagilTheme.colors.coolGray70 else BitnagilTheme.colors.white,
            )
        }

        Column(
            modifier = Modifier.size(12.dp),
        ) {
            AnimatedVisibility(
                visible = isCompleted,
                enter = scaleIn(
                    initialScale = 0f,
                    animationSpec = keyframes {
                        durationMillis = 600
                        0f at 0 using EaseOutQuart
                        1.3f at 300 using EaseInOutBack
                        1f at 600 using EaseOutBounce
                    },
                ) + fadeIn(
                    animationSpec = tween(300, easing = EaseOut),
                ),
                exit = scaleOut(
                    targetScale = 0.8f,
                    animationSpec = tween(200),
                ) + fadeOut(
                    animationSpec = tween(200),
                ),
            ) {
                BitnagilIcon(
                    id = R.drawable.ic_routine_success,
                    tint = null,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeeklyDatePickerPreview() {
    var selectedDate by remember { mutableStateOf<LocalDate>(LocalDate.now()) }

    WeeklyDatePicker(
        selectedDate = selectedDate,
        weeklyDates = selectedDate.getCurrentWeekDays(),
        routines = RoutinesUiModel(),
        onDateSelect = { selectedDate = it },
        onPreviousWeekClick = { selectedDate = selectedDate.minusWeeks(1) },
        onNextWeekClick = { selectedDate = selectedDate.plusWeeks(1) },
    )
}
