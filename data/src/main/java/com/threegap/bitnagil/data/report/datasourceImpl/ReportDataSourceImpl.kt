package com.threegap.bitnagil.data.report.datasourceImpl

import com.threegap.bitnagil.data.common.safeApiCall
import com.threegap.bitnagil.data.report.datasource.ReportDataSource
import com.threegap.bitnagil.data.report.model.request.ReportRequest
import com.threegap.bitnagil.data.report.model.response.ReportDetailResponse
import com.threegap.bitnagil.data.report.model.response.ReportHistoriesPerDateResponse
import com.threegap.bitnagil.data.report.service.ReportService
import javax.inject.Inject

class ReportDataSourceImpl @Inject constructor(
    private val reportService: ReportService,
) : ReportDataSource {
    override suspend fun submitReport(reportRequest: ReportRequest): Result<Long> {
        return safeApiCall { reportService.submitReport(reportRequest) }
    }

    override suspend fun getReports(): Result<ReportHistoriesPerDateResponse> {
        return safeApiCall { reportService.getReports() }
    }

    override suspend fun getReport(reportId: String): Result<ReportDetailResponse> {
        return safeApiCall { reportService.getReport(reportId = reportId) }
    }
}
