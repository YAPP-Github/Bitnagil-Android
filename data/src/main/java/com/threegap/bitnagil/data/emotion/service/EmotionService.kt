package com.threegap.bitnagil.data.emotion.service

import com.threegap.bitnagil.data.emotion.model.dto.EmotionDto
import com.threegap.bitnagil.data.emotion.model.request.RegisterEmotionRequest
import com.threegap.bitnagil.data.emotion.model.response.RegisterEmotionResponse
import com.threegap.bitnagil.data.emotion.model.response.TodayEmotionResponseDto
import com.threegap.bitnagil.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EmotionService {
    @GET("/api/v1/emotion-marbles")
    suspend fun getEmotions(): BaseResponse<List<EmotionDto>>

    @POST("/api/v1/emotion-marbles")
    suspend fun postEmotions(
        @Body request: RegisterEmotionRequest,
    ): BaseResponse<RegisterEmotionResponse>

    @GET("/api/v2/{searchDate}")
    suspend fun fetchTodayEmotion(
        @Path("searchDate") date: String,
    ): BaseResponse<TodayEmotionResponseDto>
}
