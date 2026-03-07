package com.threegap.bitnagil.domain.emotion.usecase

import com.threegap.bitnagil.domain.emotion.model.DailyEmotion
import com.threegap.bitnagil.domain.emotion.repository.EmotionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveDailyEmotionUseCase @Inject constructor(
    private val emotionRepository: EmotionRepository,
) {
    operator fun invoke(): Flow<DailyEmotion> = emotionRepository.dailyEmotionFlow
}
