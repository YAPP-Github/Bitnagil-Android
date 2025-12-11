package com.threegap.bitnagil.domain.report.model

import kotlinx.serialization.Serializable

@Serializable
enum class ReportStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    ;
}
