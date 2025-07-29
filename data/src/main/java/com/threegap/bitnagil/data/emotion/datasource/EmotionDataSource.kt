package com.threegap.bitnagil.data.emotion.datasource

import com.threegap.bitnagil.data.emotion.model.dto.EmotionDto
import com.threegap.bitnagil.data.emotion.model.response.RegisterEmotionResponse

interface EmotionDataSource {
    suspend fun getEmotions(): Result<List<EmotionDto>>
    suspend fun registerEmotion(emotion: String): Result<RegisterEmotionResponse>
}
