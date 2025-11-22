package com.threegap.bitnagil.domain.report.repository

import com.threegap.bitnagil.domain.report.model.Report
import com.threegap.bitnagil.domain.report.model.ReportItem
import java.time.LocalDate

interface ReportRepository {
    suspend fun submitReport(report: Report): Result<Long>
    suspend fun getReportHistories(): Result<Map<LocalDate, List<ReportItem>>>
}
