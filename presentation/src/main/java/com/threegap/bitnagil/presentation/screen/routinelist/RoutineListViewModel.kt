package com.threegap.bitnagil.presentation.screen.routinelist

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.threegap.bitnagil.domain.routine.usecase.DeleteRoutineForDayUseCase
import com.threegap.bitnagil.domain.routine.usecase.DeleteRoutineUseCase
import com.threegap.bitnagil.domain.routine.usecase.ObserveWeeklyRoutinesUseCase
import com.threegap.bitnagil.presentation.screen.home.util.getCurrentWeekDays
import com.threegap.bitnagil.presentation.screen.routinelist.contract.RoutineListSideEffect
import com.threegap.bitnagil.presentation.screen.routinelist.contract.RoutineListState
import com.threegap.bitnagil.presentation.screen.routinelist.model.RoutineUiModel
import com.threegap.bitnagil.presentation.screen.routinelist.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RoutineListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val observeWeeklyRoutinesUseCase: ObserveWeeklyRoutinesUseCase,
    private val deleteRoutineUseCase: DeleteRoutineUseCase,
    private val deleteRoutineForDayUseCase: DeleteRoutineForDayUseCase,
) : ContainerHost<RoutineListState, RoutineListSideEffect>, ViewModel() {

    override val container: Container<RoutineListState, RoutineListSideEffect> = container(initialState = RoutineListState.INIT)

    private val selectedDate = savedStateHandle.get<String>("selectedDate")
        ?.takeIf { it.isNotBlank() }
        ?.let { dateString ->
            runCatching { LocalDate.parse(dateString) }.getOrNull()
        }
        ?: LocalDate.now()

    init {
        updateDate(selectedDate)
        observeWeeklyRoutines()
    }

    fun updateDate(selectedDate: LocalDate) {
        intent {
            reduce { state.copy(selectedDate = selectedDate) }
        }
    }

    fun showDeleteConfirmBottomSheet(routine: RoutineUiModel) {
        intent {
            reduce { state.copy(selectedRoutine = routine, deleteConfirmBottomSheetVisible = true) }
        }
    }

    fun hideDeleteConfirmBottomSheet() {
        intent {
            reduce { state.copy(deleteConfirmBottomSheetVisible = false) }
        }
    }

    fun showEditConfirmBottomSheet(routine: RoutineUiModel) {
        intent {
            reduce { state.copy(selectedRoutine = routine, editConfirmBottomSheetVisible = true) }
        }
    }

    fun hideEditConfirmBottomSheet() {
        intent {
            reduce { state.copy(editConfirmBottomSheetVisible = false) }
        }
    }

    private fun observeWeeklyRoutines() {
        intent {
            reduce { state.copy(isLoading = true) }
            val weekDays = state.selectedDate.getCurrentWeekDays()
            observeWeeklyRoutinesUseCase(weekDays.first().toString(), weekDays.last().toString())
                .collect { schedule ->
                    reduce { state.copy(isLoading = false, routines = schedule.toUiModel()) }
                }
        }
    }

    fun deleteRoutineCompletely() {
        intent {
            val selectedRoutine = state.selectedRoutine ?: return@intent
            reduce { state.copy(isLoading = true) }
            deleteRoutineUseCase(selectedRoutine.routineId).fold(
                onSuccess = {
                    reduce { state.copy(isLoading = false, deleteConfirmBottomSheetVisible = false) }
                    postSideEffect(RoutineListSideEffect.ShowToast("삭제가 완료되었습니다."))
                },
                onFailure = {
                    Log.e("RoutineListViewModel", "루틴 삭제 실패: ${it.message}")
                    reduce { state.copy(isLoading = false) }
                },
            )
        }
    }

    fun deleteRoutineForToday() {
        intent {
            val selectedRoutine = state.selectedRoutine ?: return@intent
            reduce { state.copy(isLoading = true) }
            deleteRoutineForDayUseCase(selectedRoutine.routineId).fold(
                onSuccess = {
                    reduce { state.copy(isLoading = false, deleteConfirmBottomSheetVisible = false) }
                    postSideEffect(RoutineListSideEffect.ShowToast("삭제가 완료되었습니다."))
                },
                onFailure = {
                    Log.e("RoutineListViewModel", "루틴 삭제 실패: ${it.message}")
                    reduce { state.copy(isLoading = false) }
                },
            )
        }
    }

    fun navigateToAddRoutine() {
        intent {
            postSideEffect(RoutineListSideEffect.NavigateToAddRoutine)
        }
    }

    fun navigateToEditRoutine(updateFromNow: Boolean) {
        intent {
            val selectedRoutine = state.selectedRoutine ?: return@intent
            postSideEffect(
                RoutineListSideEffect.NavigateToEditRoutine(
                    routineId = selectedRoutine.routineId,
                    updateRoutineFromNowDate = updateFromNow,
                ),
            )
        }
    }

    fun navigateToBack() {
        intent {
            postSideEffect(RoutineListSideEffect.NavigateToBack)
        }
    }
}
