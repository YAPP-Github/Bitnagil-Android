package com.threegap.bitnagil.presentation.screen.summary

import android.util.Log
import androidx.lifecycle.ViewModel
import com.threegap.bitnagil.domain.activitylog.usecase.GetBadgesUseCase
import com.threegap.bitnagil.domain.activitylog.usecase.GetEmotionMarblesUseCase
import com.threegap.bitnagil.domain.emotion.usecase.GetEmotionRegisteredEventFlowUseCase
import com.threegap.bitnagil.presentation.screen.summary.contract.SummaryState
import com.threegap.bitnagil.presentation.screen.summary.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val getBadgesUseCase: GetBadgesUseCase,
    private val getEmotionMarblesUseCase: GetEmotionMarblesUseCase,
    private val getEmotionRegisteredEventFlowUseCase: GetEmotionRegisteredEventFlowUseCase,
) : ContainerHost<SummaryState, Unit>, ViewModel() {

    override val container = container<SummaryState, Unit>(initialState = SummaryState.INIT)

    init {
        onMonthChanged(YearMonth.now())
        observeEmotionRegisteredEvent()
    }

    fun onMonthChanged(newMonth: YearMonth) = intent {
        reduce { state.copy(currentMonth = newMonth) }

        // 현재, 이전, 다음 달 데이터 프리페칭. 이미 캐시된 달은 Repository가 즉시 반환한다.
        val monthsToLoad = listOf(
            newMonth.minusMonths(1),
            newMonth,
            newMonth.plusMonths(1),
        )

        coroutineScope {
            monthsToLoad.forEach { targetMonth ->
                launch { fetchBadges(targetMonth) }
                launch { fetchEmotionMarbles(targetMonth) }
            }
        }
    }

    private fun observeEmotionRegisteredEvent() = intent {
        getEmotionRegisteredEventFlowUseCase().collect { registeredMonth ->
            fetchEmotionMarbles(registeredMonth, forceRefresh = true)
        }
    }

    private suspend fun fetchBadges(yearMonth: YearMonth) {
        subIntent {
            reduce { state.copy(loadingCount = state.loadingCount + 1) }

            getBadgesUseCase(yearMonth).fold(
                onSuccess = { monthlyBadge ->
                    reduce {
                        state.copy(
                            badgesByMonth = state.badgesByMonth + (yearMonth to monthlyBadge.toUiModel()),
                            loadingCount = state.loadingCount - 1,
                        )
                    }
                },
                onFailure = {
                    Log.e("SummaryViewModel", "뱃지 가져오기 실패: ${it.message}")
                    reduce { state.copy(loadingCount = state.loadingCount - 1) }
                },
            )
        }
    }

    private suspend fun fetchEmotionMarbles(yearMonth: YearMonth, forceRefresh: Boolean = false) {
        subIntent {
            reduce { state.copy(loadingCount = state.loadingCount + 1) }

            getEmotionMarblesUseCase(
                startDate = yearMonth.atDay(1),
                endDate = yearMonth.atEndOfMonth(),
                forceRefresh = forceRefresh,
            ).fold(
                onSuccess = { marblesByDate ->
                    val emotionCells = marblesByDate.values.map { it.toUiModel() }
                    reduce {
                        state.copy(
                            emotionCellsByMonth = state.emotionCellsByMonth + (yearMonth to emotionCells),
                            loadingCount = state.loadingCount - 1,
                        )
                    }
                },
                onFailure = {
                    Log.e("SummaryViewModel", "감정 구슬 가져오기 실패: ${it.message}")
                    reduce { state.copy(loadingCount = state.loadingCount - 1) }
                },
            )
        }
    }
}
