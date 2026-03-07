package com.threegap.bitnagil.domain.emotion.repository

import com.threegap.bitnagil.domain.emotion.model.DailyEmotion
import com.threegap.bitnagil.domain.emotion.model.Emotion
import com.threegap.bitnagil.domain.emotion.model.EmotionRecommendRoutine
import kotlinx.coroutines.flow.Flow

interface EmotionRepository {
    val dailyEmotionFlow: Flow<DailyEmotion>
    suspend fun getEmotions(): Result<List<Emotion>>
    suspend fun registerEmotion(emotionMarbleType: String): Result<List<EmotionRecommendRoutine>>
    suspend fun fetchDailyEmotion(): Result<Unit>
}
