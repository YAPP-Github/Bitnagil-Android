package com.threegap.bitnagil.domain.emotion.usecase

import com.threegap.bitnagil.domain.emotion.model.MyEmotion
import com.threegap.bitnagil.domain.emotion.repository.EmotionRepository
import javax.inject.Inject

class GetMyEmotionUseCase @Inject constructor(
    private val emotionRepository: EmotionRepository,
) {
    suspend operator fun invoke(currentDate: String): Result<MyEmotion> =
        emotionRepository.getMyEmotionMarble(currentDate)
}
