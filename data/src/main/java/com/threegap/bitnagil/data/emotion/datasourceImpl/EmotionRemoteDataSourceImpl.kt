package com.threegap.bitnagil.data.emotion.datasourceImpl

import com.threegap.bitnagil.data.emotion.datasource.EmotionRemoteDataSource
import com.threegap.bitnagil.data.emotion.model.dto.EmotionDto
import com.threegap.bitnagil.data.emotion.model.request.RegisterEmotionRequest
import com.threegap.bitnagil.data.emotion.model.response.DailyEmotionResponse
import com.threegap.bitnagil.data.emotion.model.response.RegisterEmotionResponse
import com.threegap.bitnagil.data.emotion.service.EmotionService
import javax.inject.Inject

class EmotionRemoteDataSourceImpl @Inject constructor(
    private val emotionService: EmotionService,
) : EmotionRemoteDataSource {
    override suspend fun getEmotions(): Result<List<EmotionDto>> = emotionService.getEmotions()

    override suspend fun registerEmotion(emotion: String): Result<RegisterEmotionResponse> {
        val registerEmotionRequest = RegisterEmotionRequest(emotionMarbleType = emotion)
        return emotionService.postEmotions(registerEmotionRequest)
    }

    override suspend fun fetchDailyEmotion(currentDate: String): Result<DailyEmotionResponse> =
        emotionService.fetchDailyEmotion(currentDate)
}
