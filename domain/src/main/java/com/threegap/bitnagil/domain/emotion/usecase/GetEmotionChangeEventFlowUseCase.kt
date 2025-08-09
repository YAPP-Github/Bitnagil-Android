package com.threegap.bitnagil.domain.emotion.usecase

import com.threegap.bitnagil.domain.emotion.model.EmotionChangeEvent
import com.threegap.bitnagil.domain.emotion.repository.EmotionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEmotionChangeEventFlowUseCase @Inject constructor(
    private val repository: EmotionRepository,
) {
    suspend operator fun invoke(): Flow<EmotionChangeEvent> = repository.getEmotionChangeEventFlow()
}
