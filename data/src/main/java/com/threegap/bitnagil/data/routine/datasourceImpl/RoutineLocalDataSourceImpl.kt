package com.threegap.bitnagil.data.routine.datasourceImpl

import com.threegap.bitnagil.data.routine.datasource.RoutineLocalDataSource
import com.threegap.bitnagil.datastore.routine.storage.RoutineAchievementDataStore
import javax.inject.Inject

class RoutineLocalDataSourceImpl @Inject constructor(
    private val routineAchievementDataStore: RoutineAchievementDataStore,
) : RoutineLocalDataSource {

    override suspend fun markFirstRoutineCompletion(): Result<Boolean> =
        runCatching {
            routineAchievementDataStore.markFirstCompletion()
        }
}
