package com.threegap.bitnagil.presentation.routinelist

import androidx.lifecycle.SavedStateHandle
import com.threegap.bitnagil.domain.routine.usecase.FetchWeeklyRoutinesUseCase
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.routinelist.model.RoutineListIntent
import com.threegap.bitnagil.presentation.routinelist.model.RoutineListSideEffect
import com.threegap.bitnagil.presentation.routinelist.model.RoutineListState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import javax.inject.Inject

@HiltViewModel
class RoutineListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchWeeklyRoutinesUseCase: FetchWeeklyRoutinesUseCase,
) : MviViewModel<RoutineListState, RoutineListSideEffect, RoutineListIntent>(
    savedStateHandle = savedStateHandle,
    initState = RoutineListState(),
) {
    override suspend fun SimpleSyntax<RoutineListState, RoutineListSideEffect>.reduceState(
        intent: RoutineListIntent,
        state: RoutineListState,
    ): RoutineListState? {
        val newState = when (intent) {
            is RoutineListIntent.OnDateSelect -> state.copy(selectedDate = intent.date)
            is RoutineListIntent.ShowDeleteConfirmBottomSheet -> state.copy(deleteConfirmBottomSheetVisible = true)
            is RoutineListIntent.HideDeleteConfirmBottomSheet -> state.copy(deleteConfirmBottomSheetVisible = false)

            is RoutineListIntent.NavigateToBack -> {
                sendSideEffect(RoutineListSideEffect.NavigateToBack)
                null
            }
        }

        return newState
    }
}
