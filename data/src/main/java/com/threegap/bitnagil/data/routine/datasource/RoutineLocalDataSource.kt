package com.threegap.bitnagil.data.routine.datasource

import com.threegap.bitnagil.domain.routine.model.RoutineCompletionInfo
import com.threegap.bitnagil.domain.routine.model.RoutineSchedule
import kotlinx.coroutines.flow.StateFlow

interface RoutineLocalDataSource {
    val routineSchedule: StateFlow<RoutineSchedule?>
    val lastFetchRange: Pair<String, String>?
    fun saveSchedule(schedule: RoutineSchedule, startDate: String, endDate: String)
    fun getCompletionInfo(dateKey: String, routineId: String): RoutineCompletionInfo?
    fun applyOptimisticToggle(dateKey: String, routineId: String, completionInfo: RoutineCompletionInfo)
    fun clearCache()
}
