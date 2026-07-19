package com.threegap.bitnagil.data.activitylog.service

import com.threegap.bitnagil.data.activitylog.model.response.EmotionMarbleResponse
import com.threegap.bitnagil.data.activitylog.model.response.MonthlyBadgeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivityLogService {
    @GET("/api/v1/activity-logs/badges")
    suspend fun getBadges(
        @Query("year") year: Int,
        @Query("month") month: Int,
    ): Result<MonthlyBadgeResponse>

    @GET("/api/v1/activity-logs/emotion-marbles")
    suspend fun getEmotionMarbles(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
    ): Result<List<EmotionMarbleResponse>>
}
