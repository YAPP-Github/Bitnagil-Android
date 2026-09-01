package com.threegap.bitnagil.presentation.screen.summary.contract

import com.threegap.bitnagil.presentation.screen.summary.model.SummaryBadgeUiModel
import com.threegap.bitnagil.presentation.screen.summary.model.SummaryEmotionCellUiModel
import com.threegap.bitnagil.presentation.screen.summary.model.SummaryEmotionDayUiModel
import java.time.YearMonth

data class SummaryState(
    val loadingCount: Int,
    val currentMonth: YearMonth,
    val emotionCellsByMonth: Map<YearMonth, List<SummaryEmotionCellUiModel>>,
    val badgesByMonth: Map<YearMonth, SummaryBadgeUiModel>,
    val selectedEmotionDay: SummaryEmotionDayUiModel?,
) {
    val isLoading: Boolean
        get() = loadingCount > 0

    val currentMonthBadges: SummaryBadgeUiModel?
        get() = badgesByMonth[currentMonth]

    fun emotionCellsOf(yearMonth: YearMonth): List<SummaryEmotionCellUiModel> =
        emotionCellsByMonth[yearMonth].orEmpty()

    fun emotionCellsAround(yearMonth: YearMonth): List<SummaryEmotionCellUiModel> =
        emotionCellsOf(yearMonth.minusMonths(1)) + emotionCellsOf(yearMonth) + emotionCellsOf(yearMonth.plusMonths(1))

    companion object {
        val INIT = SummaryState(
            loadingCount = 0,
            currentMonth = YearMonth.now(),
            emotionCellsByMonth = emptyMap(),
            badgesByMonth = emptyMap(),
            selectedEmotionDay = null,
        )
    }
}
