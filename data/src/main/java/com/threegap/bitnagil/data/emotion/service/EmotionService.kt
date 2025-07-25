package com.threegap.bitnagil.data.emotion.service

import com.threegap.bitnagil.data.emotion.model.request.RegisterEmotionRequest
import com.threegap.bitnagil.data.emotion.model.response.GetEmotionsResponse
import com.threegap.bitnagil.data.emotion.model.response.RegisterEmotionResponse
import com.threegap.bitnagil.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EmotionService {
    @GET("/api/v1/emotion-marbles")
    suspend fun getEmotions(): BaseResponse<GetEmotionsResponse>

    @POST("/api/v1/emotion-marbles")
    suspend fun postEmotions(
        @Body request: RegisterEmotionRequest
    ): BaseResponse<RegisterEmotionResponse>
}
