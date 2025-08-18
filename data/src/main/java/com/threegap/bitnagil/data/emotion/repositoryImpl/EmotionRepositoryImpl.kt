package com.threegap.bitnagil.data.emotion.repositoryImpl

import com.threegap.bitnagil.data.emotion.datasource.EmotionDataSource
import com.threegap.bitnagil.data.emotion.model.response.toDomain
import com.threegap.bitnagil.domain.emotion.model.Emotion
import com.threegap.bitnagil.domain.emotion.model.EmotionChangeEvent
import com.threegap.bitnagil.domain.emotion.model.EmotionRecommendRoutine
import com.threegap.bitnagil.domain.emotion.model.TodayEmotion
import com.threegap.bitnagil.domain.emotion.repository.EmotionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class EmotionRepositoryImpl @Inject constructor(
    private val emotionDataSource: EmotionDataSource,
) : EmotionRepository {
    override suspend fun getEmotions(): Result<List<Emotion>> {
        return emotionDataSource.getEmotions().map { response ->
            response.map { it.toDomain() }
        }
    }

    override suspend fun registerEmotion(emotionMarbleType: String): Result<List<EmotionRecommendRoutine>> {
        return emotionDataSource.registerEmotion(emotionMarbleType).map {
            it.recommendedRoutines.map {
                    emotionRecommendedRoutineDto ->
                emotionRecommendedRoutineDto.toEmotionRecommendRoutine()
            }
        }.also {
            if (it.isSuccess) {
                _emotionChangeEventFlow.emit(EmotionChangeEvent.ChangeEmotion(emotionMarbleType))
            }
        }
    }

    override suspend fun fetchTodayEmotion(currentDate: String): Result<TodayEmotion?> =
        emotionDataSource.fetchTodayEmotion(currentDate).map { it.toDomain() }

    private val _emotionChangeEventFlow = MutableSharedFlow<EmotionChangeEvent>()
    override suspend fun getEmotionChangeEventFlow(): Flow<EmotionChangeEvent> = _emotionChangeEventFlow.asSharedFlow()
}
