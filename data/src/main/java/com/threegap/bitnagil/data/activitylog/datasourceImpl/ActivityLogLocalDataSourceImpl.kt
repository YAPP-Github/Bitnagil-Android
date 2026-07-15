package com.threegap.bitnagil.data.activitylog.datasourceImpl

import com.threegap.bitnagil.data.activitylog.datasource.ActivityLogLocalDataSource
import com.threegap.bitnagil.domain.activitylog.model.Badge
import com.threegap.bitnagil.domain.activitylog.model.EmotionMarble
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.YearMonth
import javax.inject.Inject

class ActivityLogLocalDataSourceImpl @Inject constructor() : ActivityLogLocalDataSource {
    private val _badgesByMonth = MutableStateFlow<Map<YearMonth, List<Badge>>>(emptyMap())
    override val badgesByMonth: StateFlow<Map<YearMonth, List<Badge>>> = _badgesByMonth.asStateFlow()

    private val _emotionMarblesByMonth = MutableStateFlow<Map<YearMonth, List<EmotionMarble>>>(emptyMap())
    override val emotionMarblesByMonth: StateFlow<Map<YearMonth, List<EmotionMarble>>> = _emotionMarblesByMonth.asStateFlow()

    override fun saveBadges(yearMonth: YearMonth, badges: List<Badge>) {
        _badgesByMonth.update { it + (yearMonth to badges) }
    }

    override fun saveEmotionMarbles(yearMonth: YearMonth, emotionMarbles: List<EmotionMarble>) {
        _emotionMarblesByMonth.update { it + (yearMonth to emotionMarbles) }
    }

    override fun clearCache() {
        _badgesByMonth.update { emptyMap() }
        _emotionMarblesByMonth.update { emptyMap() }
    }
}
