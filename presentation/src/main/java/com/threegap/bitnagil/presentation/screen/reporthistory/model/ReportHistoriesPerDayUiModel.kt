package com.threegap.bitnagil.presentation.screen.reporthistory.model

import java.time.LocalDate

data class ReportHistoriesPerDayUiModel(
    val date: LocalDate,
    val reports: List<ReportHistoryUiModel>,
)
