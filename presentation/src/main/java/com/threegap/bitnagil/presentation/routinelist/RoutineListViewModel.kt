package com.threegap.bitnagil.presentation.routinelist

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.routine.usecase.DeleteRoutineForDayUseCase
import com.threegap.bitnagil.domain.routine.usecase.DeleteRoutineUseCase
import com.threegap.bitnagil.domain.routine.usecase.FetchWeeklyRoutinesUseCase
import com.threegap.bitnagil.domain.writeroutine.usecase.GetWriteRoutineEventFlowUseCase
import com.threegap.bitnagil.presentation.home.util.getCurrentWeekDays
import com.threegap.bitnagil.presentation.routinelist.model.RoutineListSideEffect
import com.threegap.bitnagil.presentation.routinelist.model.RoutineListState
import com.threegap.bitnagil.presentation.routinelist.model.RoutineUiModel
import com.threegap.bitnagil.presentation.routinelist.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RoutineListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchWeeklyRoutinesUseCase: FetchWeeklyRoutinesUseCase,
    private val deleteRoutineUseCase: DeleteRoutineUseCase,
    private val deleteRoutineForDayUseCase: DeleteRoutineForDayUseCase,
    private val getWriteRoutineEventFlowUseCase: GetWriteRoutineEventFlowUseCase,
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
        fetchRoutines()
        observeRoutineChanges()
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

    private fun observeRoutineChanges() {
        viewModelScope.launch {
            getWriteRoutineEventFlowUseCase().collect {
                fetchRoutines()
            }
        }
    }

    private fun fetchRoutines() {
        intent {
            reduce { state.copy(isLoading = true) }
            val currentWeek = state.selectedDate.getCurrentWeekDays()
            val startDate = currentWeek.first().toString()
            val endDate = currentWeek.last().toString()
            fetchWeeklyRoutinesUseCase(startDate, endDate).fold(
                onSuccess = { routineSchedule ->
                    reduce { state.copy(isLoading = false, routines = routineSchedule.toUiModel()) }
                },
                onFailure = {
                    Log.e("RoutineListViewModel", "루틴 가져오기 실패: ${it.message}")
                    reduce { state.copy(isLoading = false) }
                },
            )
        }
    }

    fun deleteRoutineCompletely() {
        intent {
            reduce { state.copy(isLoading = true) }
            val selectedRoutine = state.selectedRoutine ?: return@intent
            deleteRoutineUseCase(selectedRoutine.routineId).fold(
                onSuccess = {
                    fetchRoutines()
                    reduce { state.copy(deleteConfirmBottomSheetVisible = false) }
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
            reduce { state.copy(isLoading = true) }
            val selectedRoutine = state.selectedRoutine ?: return@intent
            deleteRoutineForDayUseCase(selectedRoutine.routineId).fold(
                onSuccess = {
                    fetchRoutines()
                    reduce { state.copy(deleteConfirmBottomSheetVisible = false) }
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
