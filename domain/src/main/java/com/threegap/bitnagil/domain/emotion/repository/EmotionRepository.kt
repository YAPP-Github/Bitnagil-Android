package com.threegap.bitnagil.domain.emotion.repository

import com.threegap.bitnagil.domain.emotion.model.EmotionRecommendRoutine
import com.threegap.bitnagil.domain.emotion.model.Emotion

interface EmotionRepository {
    suspend fun getEmotions(): Result<List<Emotion>>
    suspend fun registerEmotion(emotionMarbleType: String): Result<List<EmotionRecommendRoutine>>
    suspend fun getEmotionMarble(currentDate: String): Result<Emotion?>
}
