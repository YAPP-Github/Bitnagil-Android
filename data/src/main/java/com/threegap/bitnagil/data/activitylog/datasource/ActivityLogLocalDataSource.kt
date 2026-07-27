package com.threegap.bitnagil.data.activitylog.datasource

import com.threegap.bitnagil.domain.activitylog.model.EmotionMarble
import com.threegap.bitnagil.domain.activitylog.model.MonthlyBadge
import kotlinx.coroutines.flow.StateFlow
import java.time.YearMonth

interface ActivityLogLocalDataSource {
    val badgesByMonth: StateFlow<Map<YearMonth, MonthlyBadge>>
    val emotionMarblesByMonth: StateFlow<Map<YearMonth, List<EmotionMarble>>>
    fun saveBadges(yearMonth: YearMonth, monthlyBadge: MonthlyBadge)
    fun saveEmotionMarbles(yearMonth: YearMonth, emotionMarbles: List<EmotionMarble>)
    fun removeEmotionMarbles(yearMonth: YearMonth)
    fun clearCache()
}
