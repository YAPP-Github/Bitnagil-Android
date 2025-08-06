package com.threegap.bitnagil.data.emotion.repositoryImpl

import com.threegap.bitnagil.data.emotion.datasource.EmotionDataSource
import com.threegap.bitnagil.data.emotion.model.response.toDomain
import com.threegap.bitnagil.domain.emotion.model.EmotionRecommendRoutine
import com.threegap.bitnagil.domain.emotion.model.Emotion
import com.threegap.bitnagil.domain.emotion.repository.EmotionRepository
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
        }
    }

    override suspend fun getEmotionMarble(currentDate: String): Result<Emotion?> =
        emotionDataSource.getEmotionMarble(currentDate).map { it.toDomain() }
}
