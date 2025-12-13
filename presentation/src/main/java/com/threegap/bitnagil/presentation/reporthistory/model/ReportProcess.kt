package com.threegap.bitnagil.presentation.reporthistory.model

import com.threegap.bitnagil.domain.report.model.ReportStatus

enum class ReportProcess(
    val title: String,
) {
    Total(title = "전체"),
    Reported(title = "제보 완료"),
    Progress(title = "처리 중"),
    Complete(title = "처리 완료"),
    ;

    companion object {
        fun fromDomain(status: ReportStatus): ReportProcess {
            return when (status) {
                ReportStatus.PENDING -> Reported
                ReportStatus.IN_PROGRESS -> Progress
                ReportStatus.COMPLETED -> Complete
            }
        }
    }
}
