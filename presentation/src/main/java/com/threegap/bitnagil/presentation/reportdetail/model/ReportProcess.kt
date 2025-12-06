package com.threegap.bitnagil.presentation.reportdetail.model

import com.threegap.bitnagil.domain.report.model.ReportStatus

enum class ReportProcess(
    val title: String,
) {
    Reported(title = "제보 완료"),
    Progress(title = "처리 중"),
    Complete(title = "처리 완료"),
    ;

    companion object {
        fun fromDomain(status: ReportStatus): ReportProcess {
            return when (status) {
                ReportStatus.Pending -> Reported
                ReportStatus.InProgress -> Progress
                ReportStatus.Completed -> Complete
            }
        }
    }
}
