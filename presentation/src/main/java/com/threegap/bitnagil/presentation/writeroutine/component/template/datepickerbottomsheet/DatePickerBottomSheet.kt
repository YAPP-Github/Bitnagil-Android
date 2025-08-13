package com.threegap.bitnagil.presentation.writeroutine.component.template.datepickerbottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIconButton
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.presentation.writeroutine.component.template.datepickerbottomsheet.model.CalendarUtils
import com.threegap.bitnagil.presentation.writeroutine.model.Date
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerBottomSheet(
    modifier: Modifier = Modifier,
    onDateSelected: (Date) -> Unit,
    date: Date,
    onDismiss: () -> Unit,
    availableStartDate: Date?,
    availableEndDate: Date?,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        containerColor = BitnagilTheme.colors.white,
    ){
        DatePickerBottomSheetContent(
            modifier = modifier,
            onDateSelected = { date ->
                onDateSelected(date)
                coroutineScope
                    .launch { sheetState.hide() }
                    .invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismiss()
                        }
                    }
            },
            initDate = date,
            availableStartDate = availableStartDate,
            availableEndDate = availableEndDate
        )
    }
}

@Composable
private fun DatePickerBottomSheetContent(
    modifier: Modifier = Modifier,
    onDateSelected: (Date) -> Unit,
    initDate: Date,
    availableStartDate: Date?,
    availableEndDate: Date?
) {
    var currentYear by remember { mutableIntStateOf(initDate.year) }
    var currentMonth by remember { mutableIntStateOf(initDate.month) }
    var selectedDate by remember { mutableStateOf(initDate) }

    val lastDaysOfPrevMonth = remember(initDate) {
        CalendarUtils.lastDaysOfPrevMonth(initDate.year, initDate.month)
    }
    val firstDaysOfNextMonth = remember(currentYear, currentMonth) {
        CalendarUtils.firstDaysOfNextMonth(initDate.year, initDate.month)
    }
    val currentDaysOfMonth = remember(currentYear, currentMonth) {
        CalendarUtils.getDayAmountOfMonth(initDate.year, initDate.month)
    }

    val prevMonthButtonEnabled by remember(availableStartDate) {
        derivedStateOf {
            (availableStartDate == null) || (availableStartDate.year < currentYear) || (availableStartDate.month < currentMonth)
        }
    }
    val nextMonthButtonEnabled by remember(availableEndDate) {
        derivedStateOf {
            (availableEndDate == null) || (availableEndDate.year > currentYear) || (availableEndDate.month > currentMonth)
        }
    }

    Column(
        modifier = modifier.background(
            color = BitnagilTheme.colors.white,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("${currentYear}년 ${currentMonth}월", style = BitnagilTheme.typography.title2Bold.copy(color = BitnagilTheme.colors.coolGray10))

            Row {
                BitnagilIconButton(
                    id = R.drawable.ic_back_arrow_20,
                    onClick = {
                        if (currentMonth == 1) {
                            currentMonth = 12
                            currentYear -= 1
                        } else {
                            currentMonth -= 1
                        }
                    },
                    modifier = Modifier.size(48.dp),
                    enabled = prevMonthButtonEnabled,
                    paddingValues = PaddingValues(14.dp),
                    tint = if (prevMonthButtonEnabled) BitnagilTheme.colors.coolGray10 else BitnagilTheme.colors.coolGray95,
                )

                BitnagilIconButton(
                    id = R.drawable.ic_right_arrow_20,
                    onClick = {
                        if (currentMonth == 12) {
                            currentMonth = 1
                            currentYear += 1
                        } else {
                            currentMonth += 1
                        }
                    },
                    modifier = Modifier.size(48.dp),
                    enabled = nextMonthButtonEnabled,
                    paddingValues = PaddingValues(14.dp),
                    tint = if (nextMonthButtonEnabled) BitnagilTheme.colors.coolGray10 else BitnagilTheme.colors.coolGray95,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.padding(horizontal = 20.dp)
        ){
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(CalendarUtils.dateStringList.size) { index ->
                    Text(
                        CalendarUtils.dateStringList[index],
                        modifier = Modifier.padding(bottom = 16.dp),
                        style = BitnagilTheme.typography.body2SemiBold.copy(color = BitnagilTheme.colors.coolGray50),
                        textAlign = TextAlign.Center
                    )
                }

                itemsIndexed(lastDaysOfPrevMonth) { _, day ->
                    Box(
                        modifier = Modifier.aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "$day",
                            style = BitnagilTheme.typography.subtitle1Regular.copy(color = BitnagilTheme.colors.coolGray80),
                        )
                    }
                }

                items(currentDaysOfMonth) { index ->
                    val selected = (selectedDate.year == currentYear) && (selectedDate.month == currentMonth) && (selectedDate.day == index + 1)
                    val currentDate = Date(year = currentYear, month = currentMonth, day = index + 1)
                    val available = currentDate.checkInRange(startDate = availableStartDate, endDate = availableEndDate)
                    Box(
                        modifier = Modifier.aspectRatio(1f).background(
                            color = if (selected) { BitnagilTheme.colors.orange50 } else { Color.Transparent },
                            shape = if (selected) { RoundedCornerShape(12.dp) } else { RectangleShape }
                        ).clickableWithoutRipple {
                            if (!available) return@clickableWithoutRipple
                            selectedDate = currentDate
                        },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            "${index + 1}",
                            style = if (selected) {
                                BitnagilTheme.typography.subtitle1SemiBold.copy(color = BitnagilTheme.colors.orange500)
                            } else if (available) {
                                BitnagilTheme.typography.subtitle1Regular.copy(color = BitnagilTheme.colors.coolGray10)
                            } else {
                                BitnagilTheme.typography.subtitle1Regular.copy(color = BitnagilTheme.colors.coolGray80)
                            },
                        )
                    }
                }

                itemsIndexed(firstDaysOfNextMonth) { _, day ->
                    Box(
                        modifier = Modifier.aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "$day",
                            style = BitnagilTheme.typography.subtitle1Regular.copy(color = BitnagilTheme.colors.coolGray80),
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        BitnagilTextButton(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 14.dp)
                .fillMaxWidth(),
            text = "확인",
            onClick = {
                onDateSelected(selectedDate)
            },
            enabled = true,
        )
    }
}

@Preview
@Composable
private fun DatePickerBottomSheetContentPreview(){
    BitnagilTheme{
        DatePickerBottomSheetContent(
            modifier = Modifier,
            onDateSelected = { _ -> },
            initDate = Date(year = 2025, month = 8, day = 1),
            availableStartDate = null,
            availableEndDate = null
        )
    }
}
