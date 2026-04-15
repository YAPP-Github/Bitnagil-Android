package com.threegap.bitnagil.data.emotion.datasource

import com.threegap.bitnagil.domain.emotion.model.DailyEmotion
import kotlinx.coroutines.flow.StateFlow

interface EmotionLocalDataSource {
    val dailyEmotion: StateFlow<DailyEmotion?>
    fun saveDailyEmotion(dailyEmotion: DailyEmotion)
    fun clearCache()
}
