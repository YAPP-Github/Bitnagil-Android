package com.threegap.bitnagil.presentation.screen.summary

import androidx.lifecycle.ViewModel
import com.threegap.bitnagil.presentation.screen.summary.model.SummaryEmotionCellUiModel
import com.threegap.bitnagil.presentation.screen.summary.model.SummaryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import java.time.YearMonth
import javax.inject.Inject

// 임시 UseCase 인터페이스 정의
interface GetHighlightedDaysUseCase {
    suspend operator fun invoke(year: Int, month: Int): List<SummaryEmotionCellUiModel>
}

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val getHighlightedDaysUseCase: GetHighlightedDaysUseCase
) : ContainerHost<SummaryUiState, Unit>, ViewModel() {

    override val container = container<SummaryUiState, Unit>(SummaryUiState())

    init {
        onMonthChanged(YearMonth.now())
    }

    fun onMonthChanged(newMonth: YearMonth) = intent {
        reduce { state.copy(currentMonth = newMonth) }

        // 현재, 이전, 다음 달 데이터 프리페칭
        val monthsToLoad = listOf(
            newMonth.minusMonths(1),
            newMonth,
            newMonth.plusMonths(1)
        )

        monthsToLoad.forEach { targetMonth ->
            fetchHighlightedDays(targetMonth)
        }
    }

    private fun fetchHighlightedDays(yearMonth: YearMonth) = intent {
        // 이미 데이터가 있는 경우 중복 호출 방지
        if (state.summaryEmotionDaysMap.containsKey(yearMonth)) return@intent

        runCatching {
            getHighlightedDaysUseCase(yearMonth.year, yearMonth.monthValue)
        }.onSuccess { highlightedDays ->
            reduce {
                state.copy(
                    summaryEmotionDaysMap = state.summaryEmotionDaysMap + (yearMonth to highlightedDays)
                )
            }
        }
    }
}
