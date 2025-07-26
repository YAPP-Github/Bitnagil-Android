package com.threegap.bitnagil.data.emotion.datasourceImpl

import com.threegap.bitnagil.data.common.safeApiCall
import com.threegap.bitnagil.data.emotion.datasource.EmotionDataSource
import com.threegap.bitnagil.data.emotion.model.request.RegisterEmotionRequest
import com.threegap.bitnagil.data.emotion.model.response.GetEmotionsResponse
import com.threegap.bitnagil.data.emotion.model.response.RegisterEmotionResponse
import com.threegap.bitnagil.data.emotion.service.EmotionService
import javax.inject.Inject

class EmotionDataSourceImpl @Inject constructor(
    private val emotionService: EmotionService,
) : EmotionDataSource {
    override suspend fun getEmotions(): Result<GetEmotionsResponse> {
        return safeApiCall {
            emotionService.getEmotions()
        }
    }

    override suspend fun registerEmotion(emotion: String): Result<RegisterEmotionResponse> {
        val registerEmotionRequest = RegisterEmotionRequest(emotionMarbleType = emotion)
        return safeApiCall {
            emotionService.postEmotions(registerEmotionRequest)
        }
    }
}
