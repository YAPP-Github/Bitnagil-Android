package com.threegap.bitnagil.domain.report.usecase

import com.threegap.bitnagil.domain.report.model.ReportDetail
import com.threegap.bitnagil.domain.report.repository.ReportRepository
import javax.inject.Inject

class GetReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository,
) {
    suspend operator fun invoke(id: String): Result<ReportDetail> {
        return reportRepository.getReport(reportId = id)
    }
}
