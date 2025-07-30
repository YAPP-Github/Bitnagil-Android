package com.threegap.bitnagil.domain.emotion.repository

import com.threegap.bitnagil.domain.emotion.model.Emotion
import com.threegap.bitnagil.domain.emotion.model.EmotionRecommendRoutine
import com.threegap.bitnagil.domain.emotion.model.MyEmotion

interface EmotionRepository {
    suspend fun getEmotions(): Result<List<Emotion>>
    suspend fun registerEmotion(emotion: Emotion): Result<List<EmotionRecommendRoutine>>
    suspend fun getMyEmotionMarble(currentDate: String): Result<MyEmotion>
}
