package com.threegap.bitnagil.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.presentation.common.flow.collectAsEffect
import com.threegap.bitnagil.presentation.common.toast.GlobalBitnagilToast
import com.threegap.bitnagil.presentation.home.component.template.CollapsibleHomeHeader
import com.threegap.bitnagil.presentation.home.component.template.EmptyRoutineView
import com.threegap.bitnagil.presentation.home.component.template.RoutineSection
import com.threegap.bitnagil.presentation.home.component.template.WeeklyDatePicker
import com.threegap.bitnagil.presentation.home.model.HomeIntent
import com.threegap.bitnagil.presentation.home.model.HomeSideEffect
import com.threegap.bitnagil.presentation.home.model.HomeState
import com.threegap.bitnagil.presentation.home.util.rememberCollapsibleHeaderState
import java.time.LocalDate

@Composable
fun HomeScreenContainer(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToGuide: () -> Unit,
    navigateToRegisterRoutine: () -> Unit,
    navigateToEmotion: () -> Unit,
    navigateToRoutineList: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    viewModel.sideEffectFlow.collectAsEffect { sideEffect ->
        when (sideEffect) {
            is HomeSideEffect.NavigateToGuide -> {
                navigateToGuide()
            }

            is HomeSideEffect.NavigateToRegisterRoutine -> {
                navigateToRegisterRoutine()
            }

            is HomeSideEffect.NavigateToEmotion -> {
                navigateToEmotion()
            }

            is HomeSideEffect.NavigateToRoutineList -> {
                navigateToRoutineList(sideEffect.selectedDate)
            }

            is HomeSideEffect.ShowToastWithIcon -> {
                GlobalBitnagilToast.showCheck(sideEffect.message)
            }

            is HomeSideEffect.ShowToast -> {
                GlobalBitnagilToast.show(sideEffect.message)
            }
        }
    }

    HomeScreen(
        uiState = uiState,
        onDateSelect = { date ->
            viewModel.sendIntent(HomeIntent.OnDateSelect(date))
        },
        onPreviousWeekClick = {
            viewModel.sendIntent(HomeIntent.OnPreviousWeekClick)
        },
        onNextWeekClick = {
            viewModel.sendIntent(HomeIntent.OnNextWeekClick)
        },
        onRoutineCompletionToggle = { routineId, isCompleted ->
            viewModel.toggleRoutineCompletion(routineId, isCompleted)
        },
        onSubRoutineCompletionToggle = { routineId, subRoutineIndex, isCompleted ->
            viewModel.toggleSubRoutineCompletion(routineId, subRoutineIndex, isCompleted)
        },
        onHelpClick = {
            viewModel.sendIntent(HomeIntent.OnHelpClick)
        },
        onRegisterRoutineClick = {
            viewModel.sendIntent(HomeIntent.OnRegisterRoutineClick)
        },
        onRegisterEmotionClick = {
            viewModel.sendIntent(HomeIntent.OnRegisterEmotionClick)
        },
        onShowMoreRoutinesClick = {
            viewModel.sendIntent((HomeIntent.OnShowMoreRoutinesClick))
        },
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeState,
    onDateSelect: (LocalDate) -> Unit,
    onPreviousWeekClick: () -> Unit,
    onNextWeekClick: () -> Unit,
    onRoutineCompletionToggle: (String, Boolean) -> Unit,
    onSubRoutineCompletionToggle: (String, Int, Boolean) -> Unit,
    onHelpClick: () -> Unit,
    onRegisterRoutineClick: () -> Unit,
    onRegisterEmotionClick: () -> Unit,
    onShowMoreRoutinesClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val collapsibleHeaderState = rememberCollapsibleHeaderState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BitnagilTheme.colors.coolGray10),
    ) {
        Column {
            Spacer(modifier = Modifier.height(collapsibleHeaderState.currentHeaderHeight))

            WeeklyDatePicker(
                selectedDate = uiState.selectedDate,
                weeklyDates = uiState.currentWeeks,
                routines = uiState.routines,
                onDateSelect = onDateSelect,
                onPreviousWeekClick = onPreviousWeekClick,
                onNextWeekClick = onNextWeekClick,
                modifier = Modifier
                    .background(
                        color = BitnagilTheme.colors.coolGray99,
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp,
                        ),
                    ),
            )

            if (uiState.selectedDateRoutines.isEmpty()) {
                EmptyRoutineView(
                    onRegisterRoutineClick = onRegisterRoutineClick,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BitnagilTheme.colors.coolGray99)
                        .padding(top = 62.dp),
                )
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BitnagilTheme.colors.coolGray99)
                        .padding(start = 16.dp, end = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(
                        text = "루틴 리스트",
                        color = BitnagilTheme.colors.coolGray60,
                        style = BitnagilTheme.typography.body2SemiBold,
                        modifier = Modifier.padding(top = 6.dp),
                    )
                    Text(
                        text = "더보기",
                        color = BitnagilTheme.colors.coolGray10,
                        style = BitnagilTheme.typography.body2SemiBold,
                        modifier = Modifier
                            .clickableWithoutRipple { onShowMoreRoutinesClick() }
                            .padding(vertical = 10.dp, horizontal = 12.dp),
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BitnagilTheme.colors.coolGray99)
                        .nestedScroll(collapsibleHeaderState.nestedScrollConnection)
                        .padding(horizontal = 16.dp),
                    state = collapsibleHeaderState.lazyListState,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(
                        items = uiState.selectedDateRoutines,
                        key = { routine -> "${routine.routineId}_${uiState.selectedDate}" },
                    ) { routine ->
                        RoutineSection(
                            routine = routine,
                            onRoutineToggle = { isCompleted ->
                                onRoutineCompletionToggle(routine.routineId, isCompleted)
                            },
                            onSubRoutineToggle = { subRoutineIndex, isCompleted ->
                                onSubRoutineCompletionToggle(routine.routineId, subRoutineIndex, isCompleted)
                            },
                        )
                    }
                }
            }
        }

        CollapsibleHomeHeader(
            userName = uiState.userNickname,
            todayEmotion = uiState.todayEmotion,
            collapsibleHeaderState = collapsibleHeaderState,
            onHelpClick = onHelpClick,
            onRegisterEmotion = onRegisterEmotionClick,
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeState(),
        onDateSelect = {},
        onPreviousWeekClick = {},
        onNextWeekClick = {},
        onRoutineCompletionToggle = { _, _ -> },
        onSubRoutineCompletionToggle = { _, _, _ -> },
        onHelpClick = {},
        onRegisterRoutineClick = {},
        onRegisterEmotionClick = {},
        onShowMoreRoutinesClick = {},
    )
}
