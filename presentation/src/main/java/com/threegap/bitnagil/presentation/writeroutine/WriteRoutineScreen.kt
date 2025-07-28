package com.threegap.bitnagil.presentation.writeroutine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
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
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.presentation.common.flow.collectAsEffect
import com.threegap.bitnagil.presentation.writeroutine.component.atom.namefield.NameField
import com.threegap.bitnagil.presentation.writeroutine.component.atom.selectcell.SelectCell
import com.threegap.bitnagil.presentation.writeroutine.component.atom.strokebutton.StrokeButton
import com.threegap.bitnagil.presentation.writeroutine.component.atom.tooltipbutton.TooltipButton
import com.threegap.bitnagil.presentation.writeroutine.component.block.labeledcheckbox.LabeledCheckBox
import com.threegap.bitnagil.presentation.writeroutine.component.template.TimePickerBottomSheet
import com.threegap.bitnagil.presentation.writeroutine.model.Day
import com.threegap.bitnagil.presentation.writeroutine.model.RepeatType
import com.threegap.bitnagil.presentation.writeroutine.model.SelectableDay
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

    WriteRoutineScreen(
        state = state,
        setRoutineName = viewModel::setRoutineName,
        setSubRoutineName = viewModel::setSubRoutineName,
        addSubRoutine = viewModel::addSubRoutine,
        selectRepeatTime = viewModel::selectRepeatType,
        selectDay = viewModel::selectDay,
        selectAllTime = viewModel::selectAllTime,
        showTimePickerBottomSheet = viewModel::showTimePickerBottomSheet,
        onClickRegister = viewModel::registerRoutine,
        removeSubRoutine = viewModel::removeSubRoutine,
        onClickBack = navigateToBack,
    )
}

@Composable
private fun WriteRoutineScreen(
    state: WriteRoutineState,
    setRoutineName: (String) -> Unit,
    setSubRoutineName: (Int, String) -> Unit,
    addSubRoutine: () -> Unit,
    selectRepeatTime: (RepeatType) -> Unit,
    selectDay: (Day) -> Unit,
    selectAllTime: () -> Unit,
    showTimePickerBottomSheet: () -> Unit,
    onClickRegister: () -> Unit,
    removeSubRoutine: (Int) -> Unit,
    onClickBack: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize().background(color = BitnagilTheme.colors.white),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
        ) {
            BitnagilIcon(
                id = R.drawable.ic_back_arrow_36,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickableWithoutRipple(onClick = onClickBack)
                    .align(alignment = Alignment.CenterStart)
            )


            Text(
                if (state.writeRoutineType == WriteRoutineType.ADD) "루틴 등록" else "루틴 수정",
                modifier = Modifier.align(alignment = Alignment.Center),
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.title3SemiBold,
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 32.dp)
                .weight(1f)
                .verticalScroll(state = scrollState),
        ) {
            Column {
                Row {
                    Text(
                        "루틴 이름",
                        style = BitnagilTheme.typography.body1SemiBold,
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        "*",
                        style = BitnagilTheme.typography.body1SemiBold.copy(color = BitnagilTheme.colors.error),
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                NameField(
                    value = state.routineName,
                    onValueChange = setRoutineName,
                    placeholder = "ex) 아침에 개운하게 일어나기",
                    onClickRemove = null,
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "세부 루틴",
                        color = BitnagilTheme.colors.coolGray10,
                        style = BitnagilTheme.typography.body1SemiBold,
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    TooltipButton("어려운 루틴이라면 단계별로 나눠보세요!")
                }

                Spacer(modifier = Modifier.height(14.dp))

                StrokeButton.Custom(
                    isSelected = false,
                    onClick = addSubRoutine,
                    enabled = state.addSubRoutineButtonEnabled,
                ) {
                    Row(
                        modifier = Modifier.height(52.dp).fillMaxWidth().padding(start = 24.dp, end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        BitnagilIcon(
                            id = R.drawable.ic_plus,
                            tint = BitnagilTheme.colors.navy400,
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            "세부 루틴 추가",
                            style = BitnagilTheme.typography.body2Medium.copy(color = BitnagilTheme.colors.coolGray40),
                        )
                    }
                }

                state.subRoutineNames.forEachIndexed { index, subRoutineName ->
                    Spacer(modifier = Modifier.height(10.dp))
                    NameField(
                        value = subRoutineName,
                        onValueChange = {
                            setSubRoutineName(index, it)
                        },
                        placeholder = getSubRoutinePlaceHolder(index),
                        onClickRemove = {
                            removeSubRoutine(index)
                        },
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "루틴 반복",
                        style = BitnagilTheme.typography.body1SemiBold,
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    TooltipButton(
                        "선택하지 않을 경우, 당일 루틴으로만 자동 설정돼요.",
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row {
                    StrokeButton.Text(
                        modifier = Modifier.height(52.dp).weight(1f),
                        text = "매일",
                        isSelected = state.repeatType == RepeatType.DAILY,
                        onClick = {
                            selectRepeatTime(RepeatType.DAILY)
                        },
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    StrokeButton.Text(
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

            Spacer(modifier = Modifier.height(40.dp))

            Column {
                Row {
                    Text(
                        "시작 시간",
                        style = BitnagilTheme.typography.body1SemiBold,
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        "*",
                        style = BitnagilTheme.typography.body1SemiBold.copy(color = BitnagilTheme.colors.error),
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    LabeledCheckBox(
                        label = "하루종일",
                        checked = state.selectAllTime,
                        onClick = selectAllTime,
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                StrokeButton.Custom(
                    isSelected = false,
                    onClick = showTimePickerBottomSheet,
                ) {
                    Row(
                        modifier = Modifier.height(52.dp).fillMaxWidth().padding(start = 24.dp, end = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            if (state.startTime == null) {
                                "시간 선택"
                            } else {
                                "${state.startTime.hour}".padStart(2, '0') + ":" + "${state.startTime.minute}".padStart(
                                    2,
                                    '0',
                                )
                            },
                            style = BitnagilTheme.typography.body2Medium.copy(color = BitnagilTheme.colors.coolGray40),
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        BitnagilIcon(
                            id = R.drawable.ic_down_arrow,
                            tint = BitnagilTheme.colors.navy400,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(54.dp))
        }

        BitnagilTextButton(
            text = "등록하기",
            onClick = onClickRegister,
            enabled = state.registerButtonEnabled,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 14.dp),
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
            state = WriteRoutineState(
                routineName = "이름",
                subRoutineNames = listOf(
                    "1",
                    "2",
                ),
                repeatType = RepeatType.DAILY,
                repeatDays = listOf(
                    SelectableDay(
                        day = Day.MON,
                        selected = true,
                    ),
                    SelectableDay(
                        day = Day.TUE,
                        selected = false,
                    ),
                    SelectableDay(
                        day = Day.WED,
                        selected = false,
                    ),
                    SelectableDay(
                        day = Day.THU,
                        selected = false,
                    ),
                    SelectableDay(
                        day = Day.FRI,
                        selected = false,
                    ),
                    SelectableDay(
                        day = Day.SAT,
                        selected = false,
                    ),
                    SelectableDay(
                        day = Day.SUN,
                        selected = false,
                    ),
                ),
                periodWeek = null,
                startTime = null,
                selectAllTime = false,
                loading = false,
                showTimePickerBottomSheet = false,
                writeRoutineType = WriteRoutineType.ADD,
            ),
            setRoutineName = {},
            setSubRoutineName = { _, _ -> },
            addSubRoutine = {},
            selectRepeatTime = {},
            selectDay = {},
            selectAllTime = {},
            showTimePickerBottomSheet = {},
            onClickRegister = {},
            removeSubRoutine = {},
            onClickBack = {},
        )
    }
}
