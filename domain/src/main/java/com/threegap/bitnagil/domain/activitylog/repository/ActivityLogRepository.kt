package com.threegap.bitnagil.domain.activitylog.repository

import com.threegap.bitnagil.domain.activitylog.model.EmotionMarble
import com.threegap.bitnagil.domain.activitylog.model.MonthlyBadge
import java.time.LocalDate
import java.time.YearMonth

interface ActivityLogRepository {
    suspend fun getBadges(yearMonth: YearMonth): Result<MonthlyBadge>
    suspend fun getEmotionMarbles(startDate: LocalDate, endDate: LocalDate, forceRefresh: Boolean = false): Result<List<EmotionMarble>>
    fun invalidateEmotionMarbleCache(yearMonth: YearMonth)
}
