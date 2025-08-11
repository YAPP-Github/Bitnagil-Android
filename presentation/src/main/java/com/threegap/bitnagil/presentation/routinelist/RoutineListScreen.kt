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
import com.threegap.bitnagil.presentation.home.util.getCurrentWeekDays
import com.threegap.bitnagil.presentation.routinelist.component.template.DeleteConfirmBottomSheet
import com.threegap.bitnagil.presentation.routinelist.component.template.RoutineDetailsCard
import com.threegap.bitnagil.presentation.routinelist.component.template.WeeklyDatePicker
import com.threegap.bitnagil.presentation.routinelist.model.RoutineListIntent
import com.threegap.bitnagil.presentation.routinelist.model.RoutineListSideEffect
import com.threegap.bitnagil.presentation.routinelist.model.RoutineListState
import java.time.LocalDate

@Composable
fun RoutineListScreenContainer(
    navigateToBack: () -> Unit,
    viewModel: RoutineListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    viewModel.sideEffectFlow.collectAsEffect { sideEffect ->
        when (sideEffect) {
            is RoutineListSideEffect.NavigateToBack -> navigateToBack()
        }
    }

    if (uiState.deleteConfirmBottomSheetVisible) {
        DeleteConfirmBottomSheet(
            onDismissRequest = { viewModel.sendIntent(RoutineListIntent.HideDeleteConfirmBottomSheet) },
        )
    }

    RoutineListScreen(
        uiState = uiState,
        onDateSelect = { selectedDate ->
            viewModel.sendIntent(RoutineListIntent.OnDateSelect(selectedDate))
        },
        onShowDeleteConfirmBottomSheet = {
            viewModel.sendIntent(RoutineListIntent.ShowDeleteConfirmBottomSheet)
        },
        onBackClick = {
            viewModel.sendIntent(RoutineListIntent.NavigateToBack)
        },
    )
}

@Composable
private fun RoutineListScreen(
    uiState: RoutineListState,
    onDateSelect: (LocalDate) -> Unit,
    onShowDeleteConfirmBottomSheet: () -> Unit,
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
            onDateSelect = onDateSelect,
            weeklyDates = uiState.selectedDate.getCurrentWeekDays(),
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 16.dp),
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 10.dp),
        ) {
            items(5) {
                RoutineDetailsCard(
                    onDeleteClick = onShowDeleteConfirmBottomSheet,
                )
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
        onBackClick = {},
    )
}
