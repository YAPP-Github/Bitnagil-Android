package com.threegap.bitnagil.domain.emotion.repository

import com.threegap.bitnagil.domain.emotion.model.Emotion
import com.threegap.bitnagil.domain.emotion.model.EmotionChangeEvent
import com.threegap.bitnagil.domain.emotion.model.EmotionRecommendRoutine
import com.threegap.bitnagil.domain.emotion.model.TodayEmotion
import kotlinx.coroutines.flow.Flow

interface EmotionRepository {
    suspend fun getEmotions(): Result<List<Emotion>>
    suspend fun registerEmotion(emotionMarbleType: String): Result<List<EmotionRecommendRoutine>>
    suspend fun fetchTodayEmotion(currentDate: String): Result<TodayEmotion?>
    suspend fun getEmotionChangeEventFlow(): Flow<EmotionChangeEvent>
}
