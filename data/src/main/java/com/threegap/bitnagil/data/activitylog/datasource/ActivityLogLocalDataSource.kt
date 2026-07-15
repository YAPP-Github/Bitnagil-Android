package com.threegap.bitnagil.data.activitylog.datasource

import com.threegap.bitnagil.domain.activitylog.model.Badge
import com.threegap.bitnagil.domain.activitylog.model.EmotionMarble
import kotlinx.coroutines.flow.StateFlow
import java.time.YearMonth

interface ActivityLogLocalDataSource {
    val badgesByMonth: StateFlow<Map<YearMonth, List<Badge>>>
    val emotionMarblesByMonth: StateFlow<Map<YearMonth, List<EmotionMarble>>>
    fun saveBadges(yearMonth: YearMonth, badges: List<Badge>)
    fun saveEmotionMarbles(yearMonth: YearMonth, emotionMarbles: List<EmotionMarble>)
    fun clearCache()
}
