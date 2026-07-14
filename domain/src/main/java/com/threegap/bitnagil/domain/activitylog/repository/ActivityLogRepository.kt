package com.threegap.bitnagil.domain.activitylog.repository

import com.threegap.bitnagil.domain.activitylog.model.Badge
import com.threegap.bitnagil.domain.activitylog.model.EmotionMarble
import java.time.LocalDate
import java.time.YearMonth

interface ActivityLogRepository {
    suspend fun getBadges(yearMonth: YearMonth): Result<List<Badge>>
    suspend fun getEmotionMarbles(startDate: LocalDate, endDate: LocalDate): Result<List<EmotionMarble>>
}
