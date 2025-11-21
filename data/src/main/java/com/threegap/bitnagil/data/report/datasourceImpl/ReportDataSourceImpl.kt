package com.threegap.bitnagil.data.report.datasourceImpl

import com.threegap.bitnagil.data.common.safeApiCall
import com.threegap.bitnagil.data.report.datasource.ReportDataSource
import com.threegap.bitnagil.data.report.model.request.ReportRequestDto
import com.threegap.bitnagil.data.report.service.ReportService
import javax.inject.Inject

class ReportDataSourceImpl @Inject constructor(
    private val reportService: ReportService,
) : ReportDataSource {
    override suspend fun submitReport(reportRequestDto: ReportRequestDto): Result<Long> {
        return safeApiCall { reportService.submitReport(reportRequestDto) }
    }
}
