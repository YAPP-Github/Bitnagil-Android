package com.threegap.bitnagil.presentation.screen.summary.model

import java.time.YearMonth

data class SummaryUiState(
    val currentMonth: YearMonth = YearMonth.now(),
    val summaryEmotionDaysMap: Map<YearMonth, List<SummaryEmotionCellUiModel>> = emptyMap(),
    val isLoading: Boolean = false
)
