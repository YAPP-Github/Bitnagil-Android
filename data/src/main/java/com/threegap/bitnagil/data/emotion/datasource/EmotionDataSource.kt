package com.threegap.bitnagil.data.emotion.datasource

import com.threegap.bitnagil.data.emotion.model.response.GetEmotionsResponse
import com.threegap.bitnagil.data.emotion.model.response.MyEmotionResponseDto
import com.threegap.bitnagil.data.emotion.model.response.RegisterEmotionResponse

interface EmotionDataSource {
    suspend fun getEmotions(): Result<GetEmotionsResponse>
    suspend fun registerEmotion(emotion: String): Result<RegisterEmotionResponse>
    suspend fun getMyEmotionMarble(currentDate: String): Result<MyEmotionResponseDto>
}
