package com.threegap.bitnagil.data.activitylog.datasource

import com.threegap.bitnagil.data.activitylog.model.response.EmotionMarbleResponse
import com.threegap.bitnagil.data.activitylog.model.response.MonthlyBadgeResponse

interface ActivityLogRemoteDataSource {
    suspend fun getBadges(year: Int, month: Int): Result<MonthlyBadgeResponse>
    suspend fun getEmotionMarbles(startDate: String, endDate: String): Result<List<EmotionMarbleResponse>>
}
