package com.threegap.bitnagil.data.report.datasource

import com.threegap.bitnagil.data.report.model.request.ReportRequestDto
import com.threegap.bitnagil.data.report.model.response.ReportDetailDto
import com.threegap.bitnagil.data.report.model.response.ReportHistoriesPerDateDto

interface ReportDataSource {
    suspend fun submitReport(reportRequestDto: ReportRequestDto): Result<Long>
    suspend fun getReports(): Result<ReportHistoriesPerDateDto>
    suspend fun getReport(reportId: String): Result<ReportDetailDto>
}
