package com.threegap.bitnagil.data.routine.datasourceImpl

import com.threegap.bitnagil.data.routine.datasource.RoutineLocalDataSource
import com.threegap.bitnagil.domain.routine.model.RoutineCompletionInfo
import com.threegap.bitnagil.domain.routine.model.RoutineSchedule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class RoutineLocalDataSourceImpl @Inject constructor() : RoutineLocalDataSource {
    private val _routineSchedule = MutableStateFlow<RoutineSchedule?>(null)
    override val routineSchedule: StateFlow<RoutineSchedule?> = _routineSchedule.asStateFlow()

    override var lastFetchRange: Pair<String, String>? = null
        private set

    override fun saveSchedule(schedule: RoutineSchedule, startDate: String, endDate: String) {
        lastFetchRange = startDate to endDate
        _routineSchedule.update { schedule }
    }

    override fun getCompletionInfo(dateKey: String, routineId: String): RoutineCompletionInfo? {
        val routine = _routineSchedule.value?.dailyRoutines?.get(dateKey)?.routines?.find { it.id == routineId }
        return routine?.let { RoutineCompletionInfo(routineId, it.isCompleted, it.subRoutineCompletionStates) }
    }

    override fun applyOptimisticToggle(dateKey: String, routineId: String, completionInfo: RoutineCompletionInfo) {
        _routineSchedule.update { current ->
            current ?: return@update current
            val dailyRoutines = current.dailyRoutines[dateKey] ?: return@update current
            val updatedRoutines = dailyRoutines.routines.map { routine ->
                if (routine.id == routineId) {
                    routine.copy(
                        isCompleted = completionInfo.routineCompleteYn,
                        subRoutineCompletionStates = completionInfo.subRoutineCompleteYn,
                    )
                } else {
                    routine
                }
            }
            val updatedDailyRoutines = dailyRoutines.copy(
                routines = updatedRoutines,
                isAllCompleted = updatedRoutines.all { it.isCompleted },
            )

            current.copy(
                dailyRoutines = current.dailyRoutines + (dateKey to updatedDailyRoutines),
            )
        }
    }

    override fun clearCache() {
        _routineSchedule.update { null }
    }
}
