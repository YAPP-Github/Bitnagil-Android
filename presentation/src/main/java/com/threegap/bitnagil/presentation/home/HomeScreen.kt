package com.threegap.bitnagil.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.presentation.home.component.template.CollapsibleHomeHeader
import com.threegap.bitnagil.presentation.home.component.template.DeleteConfirmDialog
import com.threegap.bitnagil.presentation.home.component.template.RoutineDetailsBottomSheet
import com.threegap.bitnagil.presentation.home.component.template.RoutineEmptyView
import com.threegap.bitnagil.presentation.home.component.template.RoutineSection
import com.threegap.bitnagil.presentation.home.component.template.RoutineSortBottomSheet
import com.threegap.bitnagil.presentation.home.component.template.WeeklyDatePicker
import com.threegap.bitnagil.presentation.home.model.HomeIntent
import com.threegap.bitnagil.presentation.home.model.HomeState
import com.threegap.bitnagil.presentation.home.model.RoutineUiModel
import com.threegap.bitnagil.presentation.home.util.rememberCollapsibleHeaderState
import java.time.LocalDate

@Composable
fun HomeScreenContainer(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    if (uiState.routineSortBottomSheetVisible) {
        RoutineSortBottomSheet(
            currentSortType = uiState.currentSortType,
            onSortTypeChange = { sortType ->
                viewModel.sendIntent(HomeIntent.OnSortTypeChange(sortType))
            },
            onDismiss = {
                viewModel.sendIntent(HomeIntent.HideRoutineSortBottomSheet)
            },
        )
    }

    uiState.selectedRoutine?.let { routine ->
        if (uiState.routineDetailsBottomSheetVisible) {
            RoutineDetailsBottomSheet(
                routine = routine,
                onDismiss = { viewModel.sendIntent(HomeIntent.HideRoutineDetailsBottomSheet) },
                // TODO: 수정 화면으로 네비게이션
                onEdit = {},
                onDelete = {
                    if (routine.repeatDay.isEmpty()) {
                        viewModel.deleteRoutine(routine.routineId)
                    } else {
                        viewModel.sendIntent(HomeIntent.ShowDeleteConfirmDialog(routine))
                    }
                },
            )
        }
    }

    uiState.deletingRoutine?.let { routine ->
        if (uiState.showDeleteConfirmDialog) {
            DeleteConfirmDialog(
                onDeleteToday = {
                    viewModel.sendIntent(HomeIntent.HideDeleteConfirmDialog)
                },
                onDeleteAll = {
                    viewModel.deleteRoutine(routine.routineId)
                },
                onDismiss = {
                    viewModel.sendIntent(HomeIntent.HideDeleteConfirmDialog)
                },
            )
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
            viewModel.sendIntent(HomeIntent.OnRoutineCompletionToggle(routineId, isCompleted))
        },
        onSubRoutineCompletionToggle = { routineId, subRoutineId, isCompleted ->
            viewModel.sendIntent(HomeIntent.OnSubRoutineCompletionToggle(routineId, subRoutineId, isCompleted))
        },
        onShowRoutineSortBottomSheet = {
            viewModel.sendIntent(HomeIntent.ShowRoutineSortBottomSheet)
        },
        onShowRoutineDetailsBottomSheet = { routine ->
            viewModel.sendIntent(HomeIntent.ShowRoutineDetailsBottomSheet(routine))
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
    onSubRoutineCompletionToggle: (String, String, Boolean) -> Unit,
    onShowRoutineSortBottomSheet: () -> Unit,
    onShowRoutineDetailsBottomSheet: (RoutineUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val collapsibleHeaderState = rememberCollapsibleHeaderState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFFEADF),
                        BitnagilTheme.colors.lightBlue75,
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(collapsibleHeaderState.screenHeight.value, collapsibleHeaderState.screenWidth.value * 2),
                ),
            ),
    ) {
        Column {
            Spacer(modifier = Modifier.height(collapsibleHeaderState.currentHeaderHeight))

            WeeklyDatePicker(
                selectedDate = uiState.selectedDate,
                weeklyDates = uiState.currentWeeks,
                onDateSelect = onDateSelect,
                onPreviousWeekClick = onPreviousWeekClick,
                onNextWeekClick = onNextWeekClick,
                modifier = Modifier
                    .background(
                        color = BitnagilTheme.colors.white,
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp,
                        ),
                    ),
            )

            LazyColumn(
                state = collapsibleHeaderState.lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(BitnagilTheme.colors.white)
                    .nestedScroll(collapsibleHeaderState.nestedScrollConnection),
            ) {
                if (uiState.selectedDateRoutines.isEmpty()) {
                    item {
                        RoutineEmptyView(
                            // todo: 루린 등록 화면으로 네비게이션
                            onRegisterRoutineClick = {},
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 62.dp),
                        )
                    }
                } else {
                    uiState.selectedDateRoutines.forEachIndexed { index, routine ->
                        item(
                            key = "${routine.routineId}_${uiState.selectedDate}",
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                RoutineSection(
                                    routine = routine,
                                    onRoutineToggle = { isCompleted ->
                                        onRoutineCompletionToggle(
                                            routine.routineId,
                                            isCompleted,
                                        )
                                    },
                                    onSubRoutineToggle = { subRoutineId, isCompleted ->
                                        onSubRoutineCompletionToggle(
                                            routine.routineId,
                                            subRoutineId,
                                            isCompleted,
                                        )
                                    },
                                    onMoreClick = {
                                        onShowRoutineDetailsBottomSheet(routine)
                                    },
                                    modifier = Modifier
                                        .padding(top = 23.dp, bottom = 10.dp)
                                        .padding(horizontal = 16.dp),
                                )

                                if (index == 0) {
                                    // todo: 아이콘 변경하기
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(end = 4.dp)
                                            .clickable { onShowRoutineSortBottomSheet() }
                                            .padding(8.dp)
                                            .size(24.dp)
                                            .zIndex(1f),
                                    )
                                }
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(110.dp))
                }
            }
        }

        CollapsibleHomeHeader(
            userName = "대현",
            collapsibleHeaderState = collapsibleHeaderState,
            onEmotionRecordClick = {},
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
        onShowRoutineSortBottomSheet = {},
        onShowRoutineDetailsBottomSheet = {},
    )
}
