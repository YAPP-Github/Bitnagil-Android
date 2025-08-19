package com.threegap.bitnagil.presentation.writeroutine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.presentation.common.flow.collectAsEffect
import com.threegap.bitnagil.presentation.writeroutine.component.atom.namefield.NameField
import com.threegap.bitnagil.presentation.writeroutine.component.atom.selectcell.SelectCell
import com.threegap.bitnagil.presentation.writeroutine.component.atom.writeroutinebutton.WriteRoutineButton
import com.threegap.bitnagil.presentation.writeroutine.component.block.expandablecontent.ExpandableContent
import com.threegap.bitnagil.presentation.writeroutine.component.block.labeledcheckbox.LabeledCheckBox
import com.threegap.bitnagil.presentation.writeroutine.component.block.routinedetailrow.RoutineDetailRow
import com.threegap.bitnagil.presentation.writeroutine.component.block.subroutinefield.SubRoutineField
import com.threegap.bitnagil.presentation.writeroutine.component.template.datepickerbottomsheet.DatePickerBottomSheet
import com.threegap.bitnagil.presentation.writeroutine.component.template.timepickerbottomsheet.TimePickerBottomSheet
import com.threegap.bitnagil.presentation.writeroutine.model.Day
import com.threegap.bitnagil.presentation.writeroutine.model.RepeatType
import com.threegap.bitnagil.presentation.writeroutine.model.Time
import com.threegap.bitnagil.presentation.writeroutine.model.WriteRoutineType
import com.threegap.bitnagil.presentation.writeroutine.model.mvi.WriteRoutineSideEffect
import com.threegap.bitnagil.presentation.writeroutine.model.mvi.WriteRoutineState

@Composable
fun WriteRoutineScreenContainer(
    viewModel: WriteRoutineViewModel = hiltViewModel(),
    navigateToBack: () -> Unit,
) {
    val state by viewModel.stateFlow.collectAsState()

    viewModel.sideEffectFlow.collectAsEffect { sideEffect ->
        when (sideEffect) {
            WriteRoutineSideEffect.MoveToPreviousScreen -> {
                navigateToBack()
            }
        }
    }

    if (state.showTimePickerBottomSheet) {
        TimePickerBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            onTimeSelected = viewModel::setStartTime,
            hour = state.startTime?.hour ?: Time.Init.hour,
            minute = state.startTime?.minute ?: Time.Init.minute,
            onDismiss = viewModel::hideTimePickerBottomSheet,
        )
    }

    if (state.showStartDatePickerBottomSheet) {
        DatePickerBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            onDateSelected = viewModel::setStartDate,
            date = state.startDate,
            onDismiss = viewModel::hideStartDatePickerBottomSheet,
            availableStartDate = null,
            availableEndDate = null,
        )
    }

    if (state.showEndDatePickerBottomSheet) {
        DatePickerBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            onDateSelected = viewModel::setEndDate,
            date = state.endDate,
            onDismiss = viewModel::hideEndDatePickerBottomSheet,
            availableStartDate = null,
            availableEndDate = null,
        )
    }

    WriteRoutineScreen(
        state = state,
        setRoutineName = viewModel::setRoutineName,
        setSubRoutineName = viewModel::setSubRoutineName,
        selectNotUseSubRoutines = viewModel::selectNotUseSubRoutines,
        selectRepeatTime = viewModel::selectRepeatType,
        selectDay = viewModel::selectDay,
        selectAllTime = viewModel::selectAllTime,
        showTimePickerBottomSheet = viewModel::showTimePickerBottomSheet,
        onClickRegister = viewModel::registerRoutine,
        onClickBack = navigateToBack,
        onClickSubRoutineExpand = viewModel::toggleSubRoutineUiExpanded,
        onClickRepeatDaysExpand = viewModel::toggleRepeatDaysUiExpanded,
        onClickStartTimeExpand = viewModel::toggleStartTimeUiExpanded,
        onClickPeriodExpand = viewModel::togglePeriodUiExpanded,
        showStartDatePickerBottomSheet = viewModel::showStartDatePickerBottomSheet,
        showEndDatePickerBottomSheet = viewModel::showEndDatePickerBottomSheet,
    )
}

@Composable
private fun WriteRoutineScreen(
    state: WriteRoutineState,
    setRoutineName: (String) -> Unit,
    setSubRoutineName: (Int, String) -> Unit,
    selectNotUseSubRoutines: () -> Unit,
    selectRepeatTime: (RepeatType) -> Unit,
    selectDay: (Day) -> Unit,
    selectAllTime: () -> Unit,
    showTimePickerBottomSheet: () -> Unit,
    onClickRegister: () -> Unit,
    onClickBack: () -> Unit,
    onClickSubRoutineExpand: () -> Unit,
    onClickRepeatDaysExpand: () -> Unit,
    onClickStartTimeExpand: () -> Unit,
    onClickPeriodExpand: () -> Unit,
    showStartDatePickerBottomSheet: () -> Unit,
    showEndDatePickerBottomSheet: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BitnagilTheme.colors.white)
            .statusBarsPadding()
            .windowInsetsPadding(WindowInsets.ime),
    ) {
        BitnagilTopBar(
            title = if (state.writeRoutineType == WriteRoutineType.Add) "루틴 등록" else "루틴 수정",
            showBackButton = true,
            onBackClick = onClickBack,
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .weight(1f)
                .verticalScroll(state = scrollState),
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            NameField(
                value = state.routineName,
                onValueChange = setRoutineName,
                placeholder = "ex) 아침에 개운하게 일어나기",
                onClickRemove = null,
            )

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                ExpandableContent(
                    expand = state.subRoutineUiExpanded,
                    required = false,
                    iconResourceId = R.drawable.img_subroutines,
                    title = "세부루틴",
                    placeHolder = "ex) 일어나자마자 이불 개기",
                    valueText = state.subRoutinesText,
                    onClick = onClickSubRoutineExpand,
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        horizontalAlignment = Alignment.End,
                    ) {
                        SubRoutineField(
                            resourceId = R.drawable.img_circle_1,
                            placeHolder = getSubRoutinePlaceHolder(0),
                            value = state.subRoutineNames.getOrNull(0) ?: "",
                            onValueChange = { setSubRoutineName(0, it) },
                            enabled = !state.selectNotUseSubRoutines,
                        )

                        SubRoutineField(
                            resourceId = R.drawable.img_circle_2,
                            placeHolder = getSubRoutinePlaceHolder(1),
                            value = state.subRoutineNames.getOrNull(1) ?: "",
                            onValueChange = { setSubRoutineName(1, it) },
                            enabled = !state.selectNotUseSubRoutines,
                        )

                        SubRoutineField(
                            resourceId = R.drawable.img_circle_3,
                            placeHolder = getSubRoutinePlaceHolder(2),
                            value = state.subRoutineNames.getOrNull(2) ?: "",
                            onValueChange = { setSubRoutineName(2, it) },
                            enabled = !state.selectNotUseSubRoutines,
                        )

                        LabeledCheckBox(
                            label = "세부 루틴 설정 안함",
                            checked = state.selectNotUseSubRoutines,
                            onClick = selectNotUseSubRoutines,
                        )
                    }
                }

                ExpandableContent(
                    expand = state.repeatDaysUiExpanded,
                    required = true,
                    iconResourceId = R.drawable.img_repeat_days,
                    title = "반복 요일",
                    placeHolder = "ex) 매주 월,화,수.목,금",
                    valueText = state.repeatDaysText,
                    onClick = onClickRepeatDaysExpand,
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(top = 14.dp, bottom = 18.dp, start = 18.dp, end = 18.dp),
                    ) {
                        Row {
                            WriteRoutineButton.Text(
                                modifier = Modifier.height(52.dp).weight(1f),
                                text = "매일",
                                isSelected = state.repeatType == RepeatType.DAILY,
                                onClick = {
                                    selectRepeatTime(RepeatType.DAILY)
                                },
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            WriteRoutineButton.Text(
                                modifier = Modifier.height(52.dp).weight(1f),
                                text = "요일 선택",
                                isSelected = state.repeatType == RepeatType.DAY,
                                onClick = {
                                    selectRepeatTime(RepeatType.DAY)
                                },
                            )
                        }

                        if (state.repeatType == RepeatType.DAY) {
                            Spacer(modifier = Modifier.height(16.dp))

                            Row {
                                state.repeatDays.forEachIndexed { index, selectableDay ->
                                    SelectCell(
                                        modifier = Modifier.weight(1f),
                                        text = selectableDay.day.text,
                                        onClick = {
                                            selectDay(selectableDay.day)
                                        },
                                        selected = selectableDay.selected,
                                    )
                                    if (index != state.repeatDays.lastIndex) {
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                ExpandableContent(
                    expand = state.periodUiExpanded,
                    required = true,
                    iconResourceId = R.drawable.img_routine_period,
                    title = "목표 기간",
                    placeHolder = "ex) 25.08.06 - 25.08.06",
                    valueText = state.periodText,
                    onClick = onClickPeriodExpand,
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(top = 14.dp, bottom = 18.dp, start = 18.dp, end = 18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        RoutineDetailRow(
                            title = "시작일",
                            placeHolder = "눌러서 선택",
                            value = state.startDate.toFormattedString(),
                            onClick = showStartDatePickerBottomSheet,
                        )

                        RoutineDetailRow(
                            title = "종료일",
                            placeHolder = "눌러서 선택",
                            value = state.endDate.toFormattedString(),
                            onClick = showEndDatePickerBottomSheet,
                        )
                    }
                }

                ExpandableContent(
                    expand = state.startTimeUiExpanded,
                    required = true,
                    iconResourceId = R.drawable.img_start_time,
                    title = "시간",
                    placeHolder = "ex) 오전 9:40부터 시작",
                    valueText = state.startTimeText,
                    onClick = onClickStartTimeExpand,
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(top = 14.dp, bottom = 18.dp, start = 18.dp, end = 18.dp),
                        horizontalAlignment = Alignment.End,
                    ) {
                        RoutineDetailRow(
                            title = "시작 시간",
                            placeHolder = "눌러서 선택",
                            value = state.startTime?.toAmPmFormattedString() ?: "",
                            onClick = showTimePickerBottomSheet,
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        LabeledCheckBox(
                            label = "하루 종일",
                            checked = state.selectAllTime,
                            onClick = selectAllTime,
                        )
                    }
                }
            }
        }

        BitnagilTextButton(
            text = "등록하기",
            onClick = onClickRegister,
            enabled = state.registerButtonEnabled,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 14.dp)
                .fillMaxWidth(),
        )
    }
}

private fun getSubRoutinePlaceHolder(index: Int): String {
    return when (index) {
        0 -> "1. ex) 일어나자마자 이불 개기"
        1 -> "2. ex) 일어나서 찬물 마시기"
        2 -> "3. ex) 세수하기"
        else -> "세부 루틴을 작성해보세요!"
    }
}

@Preview
@Composable
fun WriteRoutineScreenPreview() {
    BitnagilTheme {
        WriteRoutineScreen(
            state = WriteRoutineState.Init.copy(periodUiExpanded = true, startTimeUiExpanded = true),
            setRoutineName = {},
            setSubRoutineName = { _, _ -> },
            selectRepeatTime = {},
            selectDay = {},
            selectAllTime = {},
            showTimePickerBottomSheet = {},
            onClickRegister = {},
            onClickBack = {},
            onClickSubRoutineExpand = {},
            onClickRepeatDaysExpand = {},
            onClickStartTimeExpand = {},
            onClickPeriodExpand = {},
            showStartDatePickerBottomSheet = {},
            showEndDatePickerBottomSheet = {},
            selectNotUseSubRoutines = {},
        )
    }
}
