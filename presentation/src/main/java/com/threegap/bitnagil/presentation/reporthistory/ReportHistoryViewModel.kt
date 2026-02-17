package com.threegap.bitnagil.presentation.reporthistory

import androidx.lifecycle.ViewModel
import com.threegap.bitnagil.domain.report.model.ReportCategory
import com.threegap.bitnagil.domain.report.usecase.GetReportHistoriesUseCase
import com.threegap.bitnagil.presentation.reporthistory.contract.ReportHistorySideEffect
import com.threegap.bitnagil.presentation.reporthistory.contract.ReportHistoryState
import com.threegap.bitnagil.presentation.reporthistory.model.ReportHistoriesPerDayUiModel
import com.threegap.bitnagil.presentation.reporthistory.model.ReportStatusFilter
import com.threegap.bitnagil.presentation.reporthistory.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ReportHistoryViewModel @Inject constructor(
    private val getReportHistoriesUseCase: GetReportHistoriesUseCase,
) : ContainerHost<ReportHistoryState, ReportHistorySideEffect>, ViewModel() {
    override val container: Container<ReportHistoryState, ReportHistorySideEffect> = container(initialState = ReportHistoryState.Init)

    init {
        loadReportHistories()
    }

    private fun loadReportHistories() = intent {
        getReportHistoriesUseCase().fold(
            onSuccess = { reportHistoriesPerDate ->
                val reportHistoriesPerDays = reportHistoriesPerDate
                    .map { reportHistoryPerDateMap ->
                        ReportHistoriesPerDayUiModel(
                            date = reportHistoryPerDateMap.key,
                            reports = reportHistoryPerDateMap.value.map { it.toUiModel() },
                        )
                    }
                    .sortedByDescending { reportHistoryPerDate ->
                        reportHistoryPerDate.date
                    }

                reduce {
                    state.copy(
                        reportHistoriesPerDays = reportHistoriesPerDays,
                    )
                }
            },
            onFailure = {
            },
        )
    }

    fun selectReportCategory(reportCategory: ReportCategory) = intent {
        val currentSelectedReportCategory = state.selectedReportCategory

        reduce {
            state.copy(
                selectedReportCategory = if (currentSelectedReportCategory == reportCategory) null else reportCategory,
            )
        }
    }

    fun selectReportStatusFilter(reportStatusFilter: ReportStatusFilter) = intent {
        reduce {
            state.copy(
                selectedReportStatusFilter = reportStatusFilter,
            )
        }
    }

    fun showReportCategoryBottomSheet() = intent {
        reduce {
            state.copy(
                showSelectReportCategoryBottomSheet = true,
            )
        }
    }

    fun hideReportCategoryBottomSheet() = intent {
        reduce {
            state.copy(
                showSelectReportCategoryBottomSheet = false,
            )
        }
    }
}
