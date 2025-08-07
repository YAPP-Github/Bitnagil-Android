package com.threegap.bitnagil.domain.emotion.usecase

import com.threegap.bitnagil.domain.emotion.model.EmotionRecommendRoutine
import com.threegap.bitnagil.domain.emotion.repository.EmotionRepository
import javax.inject.Inject

class RegisterEmotionUseCase @Inject constructor(
    private val emotionRepository: EmotionRepository,
) {
    suspend operator fun invoke(emotionType: String): Result<List<EmotionRecommendRoutine>> {
        return emotionRepository.registerEmotion(emotionType)
    }
}
