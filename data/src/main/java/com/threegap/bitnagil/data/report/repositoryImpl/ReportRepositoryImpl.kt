package com.threegap.bitnagil.data.report.repositoryImpl

import com.threegap.bitnagil.data.report.datasource.ReportDataSource
import com.threegap.bitnagil.data.report.model.request.toDto
import com.threegap.bitnagil.domain.report.model.Report
import com.threegap.bitnagil.domain.report.repository.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportDataSource: ReportDataSource,
) : ReportRepository {
    override suspend fun submitReport(report: Report): Result<Long> {
        return reportDataSource.submitReport(report.toDto())
    }
}
