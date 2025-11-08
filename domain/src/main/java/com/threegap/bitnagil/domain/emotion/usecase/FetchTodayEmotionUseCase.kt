package com.threegap.bitnagil.domain.emotion.usecase

import com.threegap.bitnagil.domain.emotion.model.TodayEmotion
import com.threegap.bitnagil.domain.emotion.repository.EmotionRepository
import java.time.LocalDate
import javax.inject.Inject

class FetchTodayEmotionUseCase @Inject constructor(
    private val emotionRepository: EmotionRepository,
) {
    suspend operator fun invoke(): Result<TodayEmotion?> {
        val currentDate = LocalDate.now().toString()
        return emotionRepository.fetchTodayEmotion(currentDate)
    }
}
