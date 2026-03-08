package com.threegap.bitnagil.data.emotion.repositoryImpl

import com.threegap.bitnagil.data.emotion.datasource.EmotionDataSource
import com.threegap.bitnagil.data.emotion.model.response.toDomain
import com.threegap.bitnagil.domain.emotion.model.DailyEmotion
import com.threegap.bitnagil.domain.emotion.model.Emotion
import com.threegap.bitnagil.domain.emotion.model.EmotionRecommendRoutine
import com.threegap.bitnagil.domain.emotion.repository.EmotionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onSubscription
import java.time.LocalDate
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class EmotionRepositoryImpl @Inject constructor(
    private val emotionDataSource: EmotionDataSource,
) : EmotionRepository {

    private val isFetching = AtomicBoolean(false)
    private val _dailyEmotionFlow = MutableStateFlow(DailyEmotion.INIT)
    override val dailyEmotionFlow: Flow<DailyEmotion> = _dailyEmotionFlow
        .onSubscription {
            if (_dailyEmotionFlow.value.isStale) fetchDailyEmotion()
        }

    override suspend fun getEmotions(): Result<List<Emotion>> {
        return emotionDataSource.getEmotions().map { response ->
            response.map { it.toDomain() }
        }
    }

    override suspend fun registerEmotion(emotionMarbleType: String): Result<List<EmotionRecommendRoutine>> {
        return emotionDataSource.registerEmotion(emotionMarbleType).map {
            it.recommendedRoutines.map { emotionRecommendedRoutineDto ->
                emotionRecommendedRoutineDto.toEmotionRecommendRoutine()
            }
        }.also {
            if (it.isSuccess) fetchDailyEmotion()
        }
    }

    override suspend fun fetchDailyEmotion(): Result<Unit> {
        if (!isFetching.compareAndSet(false, true)) return Result.success(Unit)
        return try {
            val currentDate = LocalDate.now().toString()
            emotionDataSource.fetchDailyEmotion(currentDate).map {
                _dailyEmotionFlow.value = it.toDomain()
            }
        } finally {
            isFetching.set(false)
        }
    }
}
