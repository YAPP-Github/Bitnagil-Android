package com.threegap.bitnagil.domain.report.usecase

import com.threegap.bitnagil.domain.report.model.Report
import com.threegap.bitnagil.domain.report.repository.ReportRepository
import javax.inject.Inject

class SubmitReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository,
) {
    suspend operator fun invoke(report: Report): Result<Long> {
        return reportRepository.submitReport(report)
    }
}
