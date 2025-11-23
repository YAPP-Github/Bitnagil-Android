package com.threegap.bitnagil.data.report.repositoryImpl

import com.threegap.bitnagil.data.report.datasource.ReportDataSource
import com.threegap.bitnagil.data.report.model.request.toDto
import com.threegap.bitnagil.data.report.model.response.toDomain
import com.threegap.bitnagil.data.report.model.response.toDomainMap
import com.threegap.bitnagil.domain.report.model.Report
import com.threegap.bitnagil.domain.report.model.ReportDetail
import com.threegap.bitnagil.domain.report.model.ReportItem
import com.threegap.bitnagil.domain.report.repository.ReportRepository
import java.time.LocalDate
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportDataSource: ReportDataSource,
) : ReportRepository {
    override suspend fun submitReport(report: Report): Result<Long> {
        return reportDataSource.submitReport(report.toDto())
    }

    override suspend fun getReportHistories(): Result<Map<LocalDate, List<ReportItem>>> {
        return reportDataSource.getReports().map { it.toDomainMap() }
    }

    override suspend fun getReport(reportId: String): Result<ReportDetail> {
        return reportDataSource.getReport(reportId = reportId).map { it.toDomain(id = reportId) }
    }
}
