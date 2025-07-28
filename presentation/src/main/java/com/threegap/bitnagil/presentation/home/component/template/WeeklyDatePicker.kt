package com.threegap.bitnagil.presentation.home.component.template

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.presentation.home.util.formatDayOfMonth
import com.threegap.bitnagil.presentation.home.util.formatDayOfWeekShort
import com.threegap.bitnagil.presentation.home.util.formatMonthYear
import com.threegap.bitnagil.presentation.home.util.getCurrentWeekDays
import java.time.LocalDate

@Composable
fun WeeklyDatePicker(
    selectedDate: LocalDate,
    weeklyDates: List<LocalDate>,
    onDateSelect: (LocalDate) -> Unit,
    onPreviousWeekClick: () -> Unit,
    onNextWeekClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
                style = BitnagilTheme.typography.body1SemiBold,
                color = BitnagilTheme.colors.coolGray10,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .clickableWithoutRipple(onClick = onPreviousWeekClick)
                        .padding(14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BitnagilIcon(
                        id = R.drawable.ic_back_arrow_20,
                        tint = BitnagilTheme.colors.black,
                    )
                }

                Box(
                    modifier = Modifier
                        .clickableWithoutRipple(onClick = onNextWeekClick)
                        .padding(14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BitnagilIcon(
                        id = R.drawable.ic_right_arrow_20,
                        tint = BitnagilTheme.colors.black,
                    )
                }
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
                    isToday = date == LocalDate.now(),
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
            style = BitnagilTheme.typography.caption1Medium,
            color = if (!isSelected) BitnagilTheme.colors.coolGray70 else BitnagilTheme.colors.navy500,
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .size(30.dp)
                .background(
                    color = if (!isSelected) Color.Transparent else BitnagilTheme.colors.lightBlue100,
                    shape = RoundedCornerShape(8.dp),
                ),
        ) {
            Text(
                text = date.formatDayOfMonth(),
                style = BitnagilTheme.typography.body2Medium,
                color = if (!isSelected) BitnagilTheme.colors.coolGray70 else BitnagilTheme.colors.navy500,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeeklyDatePickerPreview() {
    var selectedDate by remember { mutableStateOf<LocalDate>(LocalDate.now()) }

    WeeklyDatePicker(
        selectedDate = selectedDate,
        onDateSelect = { selectedDate = it },
        weeklyDates = selectedDate.getCurrentWeekDays(),
        onPreviousWeekClick = { selectedDate = selectedDate.minusWeeks(1) },
        onNextWeekClick = { selectedDate = selectedDate.plusWeeks(1) },
    )
}
