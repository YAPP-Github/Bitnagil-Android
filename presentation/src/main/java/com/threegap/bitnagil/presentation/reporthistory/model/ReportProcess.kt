package com.threegap.bitnagil.presentation.reporthistory.model

enum class ReportProcess(
    val title: String,
) {
    Total(title = "전체"),
    Reported(title = "제보 완료"),
    Progress(title = "처리 중"),
    Complete(title = "처리 완료"),
}
