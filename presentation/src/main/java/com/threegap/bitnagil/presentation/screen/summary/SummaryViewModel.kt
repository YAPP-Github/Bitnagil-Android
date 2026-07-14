package com.threegap.bitnagil.presentation.screen.summary

import android.util.Log
import androidx.lifecycle.ViewModel
import com.threegap.bitnagil.domain.activitylog.usecase.GetBadgesUseCase
import com.threegap.bitnagil.domain.activitylog.usecase.GetEmotionMarblesUseCase
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
) : ContainerHost<SummaryState, Unit>, ViewModel() {

    override val container = container<SummaryState, Unit>(initialState = SummaryState.INIT)

    // 이미 요청한 달은 다시 부르지 않는다. 실패한 달은 제거해 다시 진입할 때 재시도되도록 한다.
    private val requestedBadgeMonths = mutableSetOf<YearMonth>()
    private val requestedEmotionMarbleMonths = mutableSetOf<YearMonth>()

    init {
        onMonthChanged(YearMonth.now())
    }

    fun onMonthChanged(newMonth: YearMonth) = intent {
        reduce { state.copy(currentMonth = newMonth) }

        // 현재, 이전, 다음 달 데이터 프리페칭
        val monthsToLoad = listOf(
            newMonth.minusMonths(1),
            newMonth,
            newMonth.plusMonths(1),
        )

        coroutineScope {
            monthsToLoad.forEach { targetMonth ->
                if (requestedBadgeMonths.add(targetMonth)) {
                    launch { fetchBadges(targetMonth) }
                }
                if (requestedEmotionMarbleMonths.add(targetMonth)) {
                    launch { fetchEmotionMarbles(targetMonth) }
                }
            }
        }
    }

    private suspend fun fetchBadges(yearMonth: YearMonth) {
        subIntent {
            reduce { state.copy(loadingCount = state.loadingCount + 1) }

            getBadgesUseCase(yearMonth).fold(
                onSuccess = { badges ->
                    reduce {
                        state.copy(
                            badgesByMonth = state.badgesByMonth + (yearMonth to badges.map { it.toUiModel() }),
                            loadingCount = state.loadingCount - 1,
                        )
                    }
                },
                onFailure = {
                    Log.e("SummaryViewModel", "뱃지 가져오기 실패: ${it.message}")
                    requestedBadgeMonths.remove(yearMonth)
                    reduce { state.copy(loadingCount = state.loadingCount - 1) }
                },
            )
        }
    }

    private suspend fun fetchEmotionMarbles(yearMonth: YearMonth) {
        subIntent {
            reduce { state.copy(loadingCount = state.loadingCount + 1) }

            getEmotionMarblesUseCase(
                startDate = yearMonth.atDay(1),
                endDate = yearMonth.atEndOfMonth(),
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
                    requestedEmotionMarbleMonths.remove(yearMonth)
                    reduce { state.copy(loadingCount = state.loadingCount - 1) }
                },
            )
        }
    }
}
