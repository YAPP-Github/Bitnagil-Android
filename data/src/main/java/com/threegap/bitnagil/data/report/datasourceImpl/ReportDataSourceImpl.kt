package com.threegap.bitnagil.data.report.datasourceImpl

import com.threegap.bitnagil.data.common.safeApiCall
import com.threegap.bitnagil.data.report.datasource.ReportDataSource
import com.threegap.bitnagil.data.report.model.request.ReportRequestDto
import com.threegap.bitnagil.data.report.model.response.ReportDetailDto
import com.threegap.bitnagil.data.report.model.response.ReportHistoriesPerDateDto
import com.threegap.bitnagil.data.report.service.ReportService
import javax.inject.Inject

class ReportDataSourceImpl @Inject constructor(
    private val reportService: ReportService,
) : ReportDataSource {
    override suspend fun submitReport(reportRequestDto: ReportRequestDto): Result<Long> {
        return safeApiCall { reportService.submitReport(reportRequestDto) }
    }

    override suspend fun getReports(): Result<ReportHistoriesPerDateDto> {
        return safeApiCall { reportService.getReports() }
    }

    override suspend fun getReport(reportId: String): Result<ReportDetailDto> {
        return safeApiCall { reportService.getReport(reportId = reportId) }
    }
}
