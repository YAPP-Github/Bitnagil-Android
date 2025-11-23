package com.threegap.bitnagil.presentation.reporthistory.model

import java.time.LocalDate

data class ReportHistoriesPerDayUiModel(
    val date: LocalDate,
    val reports: List<ReportHistoryUiModel>,
)
