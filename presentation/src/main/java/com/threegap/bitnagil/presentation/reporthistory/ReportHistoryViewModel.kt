package com.threegap.bitnagil.presentation.reporthistory

import androidx.lifecycle.ViewModel
import com.threegap.bitnagil.presentation.reporthistory.model.ReportCategory
import com.threegap.bitnagil.presentation.reporthistory.model.ReportHistoriesPerDayUiModel
import com.threegap.bitnagil.presentation.reporthistory.model.ReportHistorySideEffect
import com.threegap.bitnagil.presentation.reporthistory.model.ReportHistoryState
import com.threegap.bitnagil.presentation.reporthistory.model.ReportHistoryUiModel
import com.threegap.bitnagil.presentation.reporthistory.model.ReportProcess
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ReportHistoryViewModel @Inject constructor() : ContainerHost<ReportHistoryState, ReportHistorySideEffect>, ViewModel() {
    override val container: Container<ReportHistoryState, ReportHistorySideEffect> = container(initialState = ReportHistoryState.Init)

    init {
        loadReportHistories()
    }

    private fun loadReportHistories() = intent {
        reduce {
            state.copy(
                reportHistoriesPerDays = List(10) {
                    ReportHistoriesPerDayUiModel(
                        date = java.time.LocalDate.now(),
                        reports = listOf(
                            ReportHistoryUiModel(
                                id = "1",
                                title = "제보 1",
                                imageUrl = "-",
                                location = "서울특별시 성북구 안암로 106",
                                process = ReportProcess.Reported,
                                category = ReportCategory.Amenities,
                            ),
                            ReportHistoryUiModel(
                                id = "1",
                                title = "제보 1",
                                imageUrl = "-",
                                location = "서울특별시 성북구 안암로 106",
                                process = ReportProcess.Progress,
                                category = ReportCategory.Amenities,
                            ),
                        ),
                    )
                },
            )
        }
    }

    fun selectReportCategory(reportCategory: ReportCategory) = intent {
        val currentSelectedReportCategory = state.selectedReportCategory

        reduce {
            state.copy(
                selectedReportCategory = if (currentSelectedReportCategory == reportCategory) null else reportCategory
            )
        }
    }

    fun selectReportProcess(reportProcess: ReportProcess) = intent {
        reduce {
            state.copy(
                selectedReportProcess = reportProcess
            )
        }
    }

    fun showReportCategoryBottomSheet() = intent {
        reduce {
            state.copy(
                showSelectReportCategoryBottomSheet = true
            )
        }
    }

    fun hideReportCategoryBottomSheet() = intent {
        reduce {
            state.copy(
                showSelectReportCategoryBottomSheet = false
            )
        }
    }
}
