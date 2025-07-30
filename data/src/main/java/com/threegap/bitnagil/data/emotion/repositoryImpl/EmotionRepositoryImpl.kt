package com.threegap.bitnagil.data.emotion.repositoryImpl

import com.threegap.bitnagil.data.emotion.datasource.EmotionDataSource
import com.threegap.bitnagil.data.emotion.model.response.toDomain
import com.threegap.bitnagil.domain.emotion.model.Emotion
import com.threegap.bitnagil.domain.emotion.model.EmotionRecommendRoutine
import com.threegap.bitnagil.domain.emotion.model.MyEmotion
import com.threegap.bitnagil.domain.emotion.repository.EmotionRepository
import javax.inject.Inject

class EmotionRepositoryImpl @Inject constructor(
    private val emotionDataSource: EmotionDataSource,
) : EmotionRepository {
    override suspend fun getEmotions(): Result<List<Emotion>> {
        return emotionDataSource.getEmotions().map { response ->
            response.mapNotNull {
                when (it.emotionMarbleType) {
                    "CALM" -> Emotion.CALM
                    "VITALITY" -> Emotion.VITALITY
                    "LETHARGY" -> Emotion.LETHARGY
                    "ANXIETY" -> Emotion.ANXIETY
                    "SATISFACTION" -> Emotion.SATISFACTION
                    "FATIGUE" -> Emotion.FATIGUE
                    else -> null
                }
            }
        }
    }

    override suspend fun registerEmotion(emotion: Emotion): Result<List<EmotionRecommendRoutine>> {
        val selectedEmotion = when (emotion) {
            Emotion.CALM -> "CALM"
            Emotion.VITALITY -> "VITALITY"
            Emotion.LETHARGY -> "LETHARGY"
            Emotion.ANXIETY -> "ANXIETY"
            Emotion.SATISFACTION -> "SATISFACTION"
            Emotion.FATIGUE -> "FATIGUE"
        }

        return emotionDataSource.registerEmotion(selectedEmotion).map {
            it.recommendedRoutines.map {
                emotionRecommendedRoutineDto -> emotionRecommendedRoutineDto.toEmotionRecommendRoutine()
            }
        }
    }

    override suspend fun getMyEmotionMarble(currentDate: String): Result<MyEmotion> =
        emotionDataSource.getMyEmotionMarble(currentDate).map { it.toDomain() }
}
