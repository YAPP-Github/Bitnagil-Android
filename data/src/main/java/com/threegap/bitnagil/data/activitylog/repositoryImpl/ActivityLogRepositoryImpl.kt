package com.threegap.bitnagil.data.activitylog.repositoryImpl

import com.threegap.bitnagil.data.activitylog.datasource.ActivityLogLocalDataSource
import com.threegap.bitnagil.data.activitylog.datasource.ActivityLogRemoteDataSource
import com.threegap.bitnagil.data.activitylog.model.response.toDomain
import com.threegap.bitnagil.domain.activitylog.model.EmotionMarble
import com.threegap.bitnagil.domain.activitylog.model.MonthlyBadge
import com.threegap.bitnagil.domain.activitylog.repository.ActivityLogRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityLogRepositoryImpl @Inject constructor(
    private val activityLogRemoteDataSource: ActivityLogRemoteDataSource,
    private val activityLogLocalDataSource: ActivityLogLocalDataSource,
) : ActivityLogRepository {

    private val badgeFetchMutex = Mutex()
    private val emotionMarbleFetchMutex = Mutex()

    override suspend fun getBadges(yearMonth: YearMonth): Result<MonthlyBadge> {
        activityLogLocalDataSource.badgesByMonth.value[yearMonth]?.let { return Result.success(it) }

        return badgeFetchMutex.withLock {
            activityLogLocalDataSource.badgesByMonth.value[yearMonth]?.let { return@withLock Result.success(it) }

            activityLogRemoteDataSource.getBadges(
                year = yearMonth.year,
                month = yearMonth.monthValue,
            )
                .map { it.toDomain() }
                .onSuccess { activityLogLocalDataSource.saveBadges(yearMonth, it) }
        }
    }

    override suspend fun getEmotionMarbles(startDate: LocalDate, endDate: LocalDate, forceRefresh: Boolean): Result<List<EmotionMarble>> {
        val yearMonth = YearMonth.from(startDate)

        if (!forceRefresh) {
            activityLogLocalDataSource.emotionMarblesByMonth.value[yearMonth]?.let { return Result.success(it) }
        }

        return emotionMarbleFetchMutex.withLock {
            if (!forceRefresh) {
                activityLogLocalDataSource.emotionMarblesByMonth.value[yearMonth]?.let { return@withLock Result.success(it) }
            }

            activityLogRemoteDataSource.getEmotionMarbles(
                startDate = startDate.toString(),
                endDate = endDate.toString(),
            )
                .map { marbles -> marbles.map { it.toDomain() } }
                .onSuccess { activityLogLocalDataSource.saveEmotionMarbles(yearMonth, it) }
        }
    }
}
