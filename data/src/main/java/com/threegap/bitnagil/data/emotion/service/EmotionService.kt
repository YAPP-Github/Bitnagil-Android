package com.threegap.bitnagil.data.emotion.service

import com.threegap.bitnagil.data.emotion.model.dto.EmotionDto
import com.threegap.bitnagil.data.emotion.model.request.RegisterEmotionRequest
import com.threegap.bitnagil.data.emotion.model.response.DailyEmotionResponse
import com.threegap.bitnagil.data.emotion.model.response.RegisterEmotionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EmotionService {
    @GET("/api/v1/emotion-marbles")
    suspend fun getEmotions(): Result<List<EmotionDto>>

    @POST("/api/v1/emotion-marbles")
    suspend fun postEmotions(
        @Body request: RegisterEmotionRequest,
    ): Result<RegisterEmotionResponse>

    @GET("/api/v2/emotion-marbles/{searchDate}")
    suspend fun fetchDailyEmotion(
        @Path("searchDate") date: String,
    ): Result<DailyEmotionResponse>
}
