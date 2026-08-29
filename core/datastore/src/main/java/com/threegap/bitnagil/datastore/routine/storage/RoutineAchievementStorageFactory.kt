package com.threegap.bitnagil.datastore.routine.storage

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import com.threegap.bitnagil.datastore.routine.model.RoutineAchievement
import com.threegap.bitnagil.datastore.routine.serializer.RoutineAchievementSerializer

object RoutineAchievementStorageFactory {
    fun create(context: Context): RoutineAchievementDataStore {
        val dataStore = DataStoreFactory.create(
            serializer = RoutineAchievementSerializer,
            produceFile = { context.dataStoreFile("routine-achievement.json") },
            corruptionHandler = ReplaceFileCorruptionHandler { RoutineAchievement() },
        )
        return RoutineAchievementDataStoreImpl(dataStore)
    }
}
