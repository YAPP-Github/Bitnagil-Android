package com.threegap.bitnagil.domain.report.usecase

import com.threegap.bitnagil.domain.report.model.ReportItem
import com.threegap.bitnagil.domain.report.repository.ReportRepository
import java.time.LocalDate
import javax.inject.Inject

class GetReportHistoriesUseCase @Inject constructor(
    private val reportRepository: ReportRepository,
) {
    suspend operator fun invoke(): Result<Map<LocalDate, List<ReportItem>>> {
        return reportRepository.getReportHistories()
    }
}
