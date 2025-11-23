package com.threegap.bitnagil.data.report.datasource

import com.threegap.bitnagil.data.report.model.request.ReportRequestDto
import com.threegap.bitnagil.data.report.model.response.ReportHistoriesPerDateDto

interface ReportDataSource {
    suspend fun submitReport(reportRequestDto: ReportRequestDto): Result<Long>
    suspend fun getReports(): Result<ReportHistoriesPerDateDto>
}
