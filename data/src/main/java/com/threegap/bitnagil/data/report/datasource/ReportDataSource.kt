package com.threegap.bitnagil.data.report.datasource

import com.threegap.bitnagil.data.report.model.request.ReportRequest
import com.threegap.bitnagil.data.report.model.response.ReportDetailResponse
import com.threegap.bitnagil.data.report.model.response.ReportHistoriesPerDateResponse

interface ReportDataSource {
    suspend fun submitReport(reportRequest: ReportRequest): Result<Long>
    suspend fun getReports(): Result<ReportHistoriesPerDateResponse>
    suspend fun getReport(reportId: String): Result<ReportDetailResponse>
}
