package com.threegap.bitnagil.data.emotion.datasource

import com.threegap.bitnagil.data.emotion.model.dto.EmotionDto
import com.threegap.bitnagil.data.emotion.model.response.RegisterEmotionResponse
import com.threegap.bitnagil.data.emotion.model.response.TodayEmotionResponseDto

interface EmotionDataSource {
    suspend fun getEmotions(): Result<List<EmotionDto>>
    suspend fun registerEmotion(emotion: String): Result<RegisterEmotionResponse>
    suspend fun fetchTodayEmotion(currentDate: String): Result<TodayEmotionResponseDto>
}
