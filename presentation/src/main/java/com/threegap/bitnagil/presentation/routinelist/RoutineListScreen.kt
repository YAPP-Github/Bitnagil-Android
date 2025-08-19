package com.threegap.bitnagil.presentation.routinelist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.presentation.common.flow.collectAsEffect
import com.threegap.bitnagil.presentation.routinelist.component.template.DeleteConfirmBottomSheet
import com.threegap.bitnagil.presentation.routinelist.component.template.EditConfirmBottomSheet
import com.threegap.bitnagil.presentation.routinelist.component.template.EmptyRoutineListView
import com.threegap.bitnagil.presentation.routinelist.component.template.RoutineDetailsCard
import com.threegap.bitnagil.presentation.routinelist.component.template.WeeklyDatePicker
import com.threegap.bitnagil.presentation.routinelist.model.RoutineListIntent
import com.threegap.bitnagil.presentation.routinelist.model.RoutineListSideEffect
import com.threegap.bitnagil.presentation.routinelist.model.RoutineListState
import com.threegap.bitnagil.presentation.routinelist.model.RoutineUiModel
import java.time.LocalDate

@Composable
fun RoutineListScreenContainer(
    navigateToBack: () -> Unit,
    navigateToEditRoutine: (String, Boolean) -> Unit,
    navigateToAddRoutine: () -> Unit,
    viewModel: RoutineListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    viewModel.sideEffectFlow.collectAsEffect { sideEffect ->
        when (sideEffect) {
            is RoutineListSideEffect.NavigateToBack -> navigateToBack()
            is RoutineListSideEffect.NavigateToAddRoutine -> {
                navigateToAddRoutine()
            }
            is RoutineListSideEffect.NavigateToEditRoutine -> {
                navigateToEditRoutine(sideEffect.routineId, sideEffect.updateRoutineFromNowDate)
            }
        }
    }

    if (uiState.deleteConfirmBottomSheetVisible) {
        uiState.selectedRoutine?.let { routine ->
            DeleteConfirmBottomSheet(
                isRepeatRoutine = routine.repeatDay.isNotEmpty(),
                onDismissRequest = { viewModel.sendIntent(RoutineListIntent.HideDeleteConfirmBottomSheet) },
                onDeleteToday = viewModel::deleteRoutineForToday,
                onDeleteAll = viewModel::deleteRoutineCompletely,
                onCancel = { viewModel.sendIntent(RoutineListIntent.HideDeleteConfirmBottomSheet) },
            )
        }
    }

    if (uiState.editConfirmBottomSheetVisible) {
        uiState.selectedRoutine?.let {
            EditConfirmBottomSheet(
                onDismissRequest = { viewModel.sendIntent(RoutineListIntent.HideEditConfirmBottomSheet) },
                onApplyToday = { viewModel.sendIntent(RoutineListIntent.OnApplyTodayClick) },
                onApplyTomorrow = { viewModel.sendIntent(RoutineListIntent.OnApplyTomorrowClick) },
            )
        }
    }

    RoutineListScreen(
        uiState = uiState,
        onDateSelect = { selectedDate ->
            viewModel.sendIntent(RoutineListIntent.OnDateSelect(selectedDate))
        },
        onShowDeleteConfirmBottomSheet = { routine ->
            viewModel.sendIntent(RoutineListIntent.ShowDeleteConfirmBottomSheet(routine))
        },
        onShowEditConfirmBottomSheet = { routine ->
            viewModel.sendIntent(RoutineListIntent.ShowEditConfirmBottomSheet(routine))
        },
        onRegisterRoutineClick = { viewModel.sendIntent(RoutineListIntent.OnRegisterRoutineClick) },
        onBackClick = { viewModel.sendIntent(RoutineListIntent.NavigateToBack) },
    )
}

@Composable
private fun RoutineListScreen(
    uiState: RoutineListState,
    onDateSelect: (LocalDate) -> Unit,
    onShowDeleteConfirmBottomSheet: (RoutineUiModel) -> Unit,
    onShowEditConfirmBottomSheet: (RoutineUiModel) -> Unit,
    onRegisterRoutineClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BitnagilTheme.colors.coolGray99)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BitnagilTopBar(
            title = "루틴리스트",
            showBackButton = true,
            onBackClick = onBackClick,
        )

        Spacer(modifier = Modifier.height(4.dp))

        WeeklyDatePicker(
            selectedDate = uiState.selectedDate,
            weeklyDates = uiState.currentWeekDates,
            onDateSelect = onDateSelect,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 16.dp),
        )

        if (uiState.selectedDateRoutines.isEmpty()) {
            EmptyRoutineListView(
                onRegisterRoutineClick = onRegisterRoutineClick,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 10.dp),
            ) {
                items(
                    items = uiState.selectedDateRoutines,
                    key = { routine -> routine.routineId },
                    contentType = { "routine_item" },
                ) { routine ->
                    RoutineDetailsCard(
                        routine = routine,
                        onEditClick = { onShowEditConfirmBottomSheet(routine) },
                        onDeleteClick = { onShowDeleteConfirmBottomSheet(routine) },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RoutineListScreenPreview() {
    RoutineListScreen(
        uiState = RoutineListState(),
        onDateSelect = {},
        onShowDeleteConfirmBottomSheet = {},
        onShowEditConfirmBottomSheet = {},
        onRegisterRoutineClick = {},
        onBackClick = {},
    )
}
