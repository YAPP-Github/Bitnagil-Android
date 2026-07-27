package com.threegap.bitnagil.domain.emotion.usecase

import com.threegap.bitnagil.domain.activitylog.repository.ActivityLogRepository
import com.threegap.bitnagil.domain.emotion.model.EmotionRecommendRoutine
import com.threegap.bitnagil.domain.emotion.repository.EmotionRepository
import java.time.YearMonth
import javax.inject.Inject

class RegisterEmotionUseCase @Inject constructor(
    private val emotionRepository: EmotionRepository,
    private val activityLogRepository: ActivityLogRepository,
) {
    suspend operator fun invoke(emotionType: String): Result<List<EmotionRecommendRoutine>> {
        return emotionRepository.registerEmotion(emotionType).onSuccess {
            activityLogRepository.invalidateEmotionMarbleCache(YearMonth.now())
        }
    }
}
