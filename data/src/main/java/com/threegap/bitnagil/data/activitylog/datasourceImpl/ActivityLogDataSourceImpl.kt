package com.threegap.bitnagil.data.activitylog.datasourceImpl

import com.threegap.bitnagil.data.activitylog.datasource.ActivityLogDataSource
import com.threegap.bitnagil.data.activitylog.model.response.BadgeResponse
import com.threegap.bitnagil.data.activitylog.model.response.EmotionMarbleResponse
import com.threegap.bitnagil.data.activitylog.service.ActivityLogService
import javax.inject.Inject

class ActivityLogDataSourceImpl @Inject constructor(
    private val activityLogService: ActivityLogService,
) : ActivityLogDataSource {
    override suspend fun getBadges(year: Int, month: Int): Result<List<BadgeResponse>> =
        activityLogService.getBadges(year = year, month = month)

    override suspend fun getEmotionMarbles(startDate: String, endDate: String): Result<List<EmotionMarbleResponse>> =
        activityLogService.getEmotionMarbles(startDate = startDate, endDate = endDate)
}
