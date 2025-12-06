package com.threegap.bitnagil.domain.report.model

enum class ReportStatus {
    Pending,
    InProgress,
    Completed,
    ;

    companion object {
        fun fromString(value: String): ReportStatus {
            return when (value) {
                "PENDING" -> Pending
                "IN_PROGRESS" -> InProgress
                "COMPLETED" -> Completed
                else -> throw IllegalArgumentException("Invalid ReportStatus value: $value")
            }
        }

        fun toString(value: ReportStatus): String {
            return when (value) {
                Pending -> "PENDING"
                InProgress -> "IN_PROGRESS"
                Completed -> "COMPLETED"
            }
        }
    }
}
