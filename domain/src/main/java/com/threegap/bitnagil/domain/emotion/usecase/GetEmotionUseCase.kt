package com.threegap.bitnagil.domain.emotion.usecase

import com.threegap.bitnagil.domain.emotion.model.Emotion
import com.threegap.bitnagil.domain.emotion.repository.EmotionRepository
import javax.inject.Inject

class GetEmotionUseCase @Inject constructor(
    private val emotionRepository: EmotionRepository,
) {
    suspend operator fun invoke(currentDate: String): Result<Emotion?> =
        emotionRepository.getEmotionMarble(currentDate)
}
