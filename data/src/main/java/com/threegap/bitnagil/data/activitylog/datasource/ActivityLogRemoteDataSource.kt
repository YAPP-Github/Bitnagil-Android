package com.threegap.bitnagil.data.activitylog.datasource

import com.threegap.bitnagil.data.activitylog.model.response.BadgeResponse
import com.threegap.bitnagil.data.activitylog.model.response.EmotionMarbleResponse

interface ActivityLogRemoteDataSource {
    suspend fun getBadges(year: Int, month: Int): Result<List<BadgeResponse>>
    suspend fun getEmotionMarbles(startDate: String, endDate: String): Result<List<EmotionMarbleResponse>>
}
