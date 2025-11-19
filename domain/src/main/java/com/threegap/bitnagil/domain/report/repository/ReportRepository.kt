package com.threegap.bitnagil.domain.report.repository

import com.threegap.bitnagil.domain.report.model.Report

interface ReportRepository {
    suspend fun submitReport(report: Report): Result<Long>
}
