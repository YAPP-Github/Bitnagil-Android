package com.threegap.bitnagil.data.activitylog.datasourceImpl

import com.threegap.bitnagil.data.activitylog.datasource.ActivityLogRemoteDataSource
import com.threegap.bitnagil.data.activitylog.model.response.EmotionMarbleResponse
import com.threegap.bitnagil.data.activitylog.model.response.MonthlyBadgeResponse
import com.threegap.bitnagil.data.activitylog.service.ActivityLogService
import javax.inject.Inject

class ActivityLogRemoteDataSourceImpl @Inject constructor(
    private val activityLogService: ActivityLogService,
) : ActivityLogRemoteDataSource {
    override suspend fun getBadges(year: Int, month: Int): Result<MonthlyBadgeResponse> =
        activityLogService.getBadges(year = year, month = month)

    override suspend fun getEmotionMarbles(startDate: String, endDate: String): Result<List<EmotionMarbleResponse>> =
        activityLogService.getEmotionMarbles(startDate = startDate, endDate = endDate)
}
