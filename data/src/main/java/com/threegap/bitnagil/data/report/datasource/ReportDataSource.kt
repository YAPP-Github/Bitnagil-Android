package com.threegap.bitnagil.data.report.datasource

import com.threegap.bitnagil.data.report.model.request.ReportRequestDto

interface ReportDataSource {
    suspend fun submitReport(reportRequestDto: ReportRequestDto): Result<Long>
}
