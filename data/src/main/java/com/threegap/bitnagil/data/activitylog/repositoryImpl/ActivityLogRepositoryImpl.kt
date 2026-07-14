package com.threegap.bitnagil.data.activitylog.repositoryImpl

import com.threegap.bitnagil.data.activitylog.datasource.ActivityLogDataSource
import com.threegap.bitnagil.data.activitylog.model.response.toDomain
import com.threegap.bitnagil.domain.activitylog.model.Badge
import com.threegap.bitnagil.domain.activitylog.model.EmotionMarble
import com.threegap.bitnagil.domain.activitylog.repository.ActivityLogRepository
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

class ActivityLogRepositoryImpl @Inject constructor(
    private val activityLogDataSource: ActivityLogDataSource,
) : ActivityLogRepository {
    override suspend fun getBadges(yearMonth: YearMonth): Result<List<Badge>> {
        return activityLogDataSource.getBadges(
            year = yearMonth.year,
            month = yearMonth.monthValue,
        ).map { badges -> badges.map { it.toDomain() } }
    }

    override suspend fun getEmotionMarbles(startDate: LocalDate, endDate: LocalDate): Result<List<EmotionMarble>> {
        return activityLogDataSource.getEmotionMarbles(
            startDate = startDate.toString(),
            endDate = endDate.toString(),
        ).map { marbles -> marbles.map { it.toDomain() } }
    }
}
