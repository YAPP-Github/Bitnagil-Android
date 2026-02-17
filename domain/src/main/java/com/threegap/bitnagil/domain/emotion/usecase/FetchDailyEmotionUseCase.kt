package com.threegap.bitnagil.domain.emotion.usecase

import com.threegap.bitnagil.domain.emotion.model.DailyEmotion
import com.threegap.bitnagil.domain.emotion.repository.EmotionRepository
import java.time.LocalDate
import javax.inject.Inject

class FetchDailyEmotionUseCase @Inject constructor(
    private val emotionRepository: EmotionRepository,
) {
    suspend operator fun invoke(): Result<DailyEmotion> {
        val currentDate = LocalDate.now().toString()
        return emotionRepository.fetchDailyEmotion(currentDate)
    }
}
