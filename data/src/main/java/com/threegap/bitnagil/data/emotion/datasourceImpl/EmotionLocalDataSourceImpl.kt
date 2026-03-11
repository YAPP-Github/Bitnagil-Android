package com.threegap.bitnagil.data.emotion.datasourceImpl

import com.threegap.bitnagil.data.emotion.datasource.EmotionLocalDataSource
import com.threegap.bitnagil.domain.emotion.model.DailyEmotion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class EmotionLocalDataSourceImpl @Inject constructor() : EmotionLocalDataSource {
    private val _dailyEmotion = MutableStateFlow<DailyEmotion?>(null)
    override val dailyEmotion: StateFlow<DailyEmotion?> = _dailyEmotion.asStateFlow()

    override fun saveDailyEmotion(dailyEmotion: DailyEmotion) {
        _dailyEmotion.update { dailyEmotion }
    }

    override fun clearCache() {
        _dailyEmotion.update { null }
    }
}
