package com.threegap.bitnagil.domain.emotion.usecase

import com.threegap.bitnagil.domain.emotion.repository.EmotionRepository
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth
import javax.inject.Inject

class GetEmotionRegisteredEventFlowUseCase @Inject constructor(
    private val emotionRepository: EmotionRepository,
) {
    suspend operator fun invoke(): Flow<YearMonth> = emotionRepository.getEmotionRegisteredEventFlow()
}
