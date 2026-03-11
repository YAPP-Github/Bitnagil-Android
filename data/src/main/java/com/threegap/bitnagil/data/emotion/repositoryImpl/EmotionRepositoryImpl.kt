package com.threegap.bitnagil.data.emotion.repositoryImpl

import com.threegap.bitnagil.data.emotion.datasource.EmotionLocalDataSource
import com.threegap.bitnagil.data.emotion.datasource.EmotionRemoteDataSource
import com.threegap.bitnagil.data.emotion.model.response.toDomain
import com.threegap.bitnagil.domain.emotion.model.DailyEmotion
import com.threegap.bitnagil.domain.emotion.model.Emotion
import com.threegap.bitnagil.domain.emotion.model.EmotionRecommendRoutine
import com.threegap.bitnagil.domain.emotion.repository.EmotionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.LocalDate
import javax.inject.Inject

class EmotionRepositoryImpl @Inject constructor(
    private val emotionRemoteDataSource: EmotionRemoteDataSource,
    private val emotionLocalDateSource: EmotionLocalDataSource,
) : EmotionRepository {

    private val fetchMutex = Mutex()

    override suspend fun getEmotions(): Result<List<Emotion>> {
        return emotionRemoteDataSource.getEmotions().map { response ->
            response.map { it.toDomain() }
        }
    }

    override suspend fun registerEmotion(emotionMarbleType: String): Result<List<EmotionRecommendRoutine>> {
        return emotionRemoteDataSource.registerEmotion(emotionMarbleType).map {
            it.recommendedRoutines.map { emotionRecommendedRoutineDto ->
                emotionRecommendedRoutineDto.toEmotionRecommendRoutine()
            }
        }.also {
            if (it.isSuccess) fetchAndSaveDailyEmotion(today = LocalDate.now(), forceRefresh = true)
        }
    }

    override fun observeDailyEmotion(): Flow<Result<DailyEmotion>> = flow {
        fetchAndSaveDailyEmotion(LocalDate.now())
            .onFailure {
                emit(Result.failure(it))
                return@flow
            }

        emitAll(
            emotionLocalDateSource.dailyEmotion
                .filterNotNull()
                .map { Result.success(it) },
        )
    }

    override fun clearCache() {
        emotionLocalDateSource.clearCache()
    }

    private suspend fun fetchAndSaveDailyEmotion(
        today: LocalDate,
        forceRefresh: Boolean = false,
    ): Result<DailyEmotion> {
        if (!forceRefresh) {
            val currentLocalData = emotionLocalDateSource.dailyEmotion.value
            if (currentLocalData != null && !currentLocalData.isStale(today)) {
                return Result.success(currentLocalData)
            }
        }

        return fetchMutex.withLock {
            if (!forceRefresh) {
                val doubleCheckData = emotionLocalDateSource.dailyEmotion.value
                if (doubleCheckData != null && !doubleCheckData.isStale(today)) {
                    return@withLock Result.success(doubleCheckData)
                }
            }
            emotionRemoteDataSource.fetchDailyEmotion(today.toString())
                .onSuccess { emotionLocalDateSource.saveDailyEmotion(it.toDomain(today)) }
                .map { it.toDomain(today) }
        }
    }
}
