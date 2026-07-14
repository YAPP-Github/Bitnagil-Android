package com.threegap.bitnagil.domain.activitylog.usecase

import com.threegap.bitnagil.domain.activitylog.model.Badge
import com.threegap.bitnagil.domain.activitylog.repository.ActivityLogRepository
import java.time.YearMonth
import javax.inject.Inject

class GetBadgesUseCase @Inject constructor(
    private val activityLogRepository: ActivityLogRepository,
) {
    suspend operator fun invoke(yearMonth: YearMonth): Result<List<Badge>> {
        return activityLogRepository.getBadges(yearMonth = yearMonth)
    }
}
