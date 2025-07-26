package com.threegap.bitnagil.domain.emotion.repository

import com.threegap.bitnagil.domain.emotion.model.Emotion

interface EmotionRepository {
    suspend fun getEmotions(): Result<List<Emotion>>
    suspend fun registerEmotion(emotion: Emotion): Result<Unit>
}
