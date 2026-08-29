package com.threegap.bitnagil.datastore.routine.storage

interface RoutineAchievementDataStore {
    suspend fun markFirstCompletion(): Boolean
}
