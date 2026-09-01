package com.threegap.bitnagil.domain.activitylog.usecase

import com.threegap.bitnagil.domain.activitylog.model.EmotionMarble
import com.threegap.bitnagil.domain.activitylog.repository.ActivityLogRepository
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

class GetEmotionMarblesUseCase @Inject constructor(
    private val activityLogRepository: ActivityLogRepository,
) {
    suspend operator fun invoke(
        yearMonth: YearMonth,
        forceRefresh: Boolean = false,
    ): Result<Map<LocalDate, EmotionMarble>> {
        return activityLogRepository.getEmotionMarbles(yearMonth = yearMonth, forceRefresh = forceRefresh)
            .map { marbles -> marbles.associateBy { it.date } }
    }
}
