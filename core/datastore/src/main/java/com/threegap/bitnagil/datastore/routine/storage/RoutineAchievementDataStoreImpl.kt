package com.threegap.bitnagil.datastore.routine.storage

import androidx.datastore.core.DataStore
import com.threegap.bitnagil.datastore.routine.model.RoutineAchievement

class RoutineAchievementDataStoreImpl(
    private val dataStore: DataStore<RoutineAchievement>,
) : RoutineAchievementDataStore {

    override suspend fun markFirstCompletion(): Boolean {
        var isFirstCompletion = false

        dataStore.updateData { currentAchievement ->
            isFirstCompletion = !currentAchievement.firstCompletionLogged
            currentAchievement.copy(firstCompletionLogged = true)
        }

        return isFirstCompletion
    }
}
