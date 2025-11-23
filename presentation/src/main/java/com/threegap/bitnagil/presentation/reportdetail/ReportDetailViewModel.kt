package com.threegap.bitnagil.presentation.reportdetail

import androidx.lifecycle.ViewModel
import com.threegap.bitnagil.domain.report.usecase.GetReportUseCase
import com.threegap.bitnagil.presentation.reportdetail.model.ReportCategory
import com.threegap.bitnagil.presentation.reportdetail.model.ReportProcess
import com.threegap.bitnagil.presentation.reportdetail.model.mvi.ReportDetailSideEffect
import com.threegap.bitnagil.presentation.reportdetail.model.mvi.ReportDetailState
import com.threegap.bitnagil.presentation.reportdetail.model.navarg.ReportDetailScreenArg
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel(assistedFactory = ReportDetailViewModel.Factory::class)
class ReportDetailViewModel @AssistedInject constructor(
    private val getReportDetailUseCase: GetReportUseCase,
    @Assisted private val reportDetailArg: ReportDetailScreenArg,
) : ContainerHost<ReportDetailState, ReportDetailSideEffect>, ViewModel() {
    override val container: Container<ReportDetailState, ReportDetailSideEffect> = container(initialState = ReportDetailState.Init)

    @AssistedFactory
    interface Factory {
        fun create(reportDetailArg: ReportDetailScreenArg): ReportDetailViewModel
    }

    init {
        loadReportDetail(reportId = reportDetailArg.reportId)
    }

    private fun loadReportDetail(reportId: String) = intent {
        getReportDetailUseCase(id = reportId).fold(
            onSuccess = { reportDetail ->
                reduce {
                    state.copy(
                        reportProcess = ReportProcess.fromDomain(reportDetail.status),
                        reportTitle = reportDetail.title,
                        reportContent = reportDetail.content,
                        reportCategory = ReportCategory.fromDomain(reportDetail.category),
                        imageUrls = reportDetail.imageUrls,
                        location = reportDetail.address,
                        date = reportDetail.date,
                    )
                }
            },
            onFailure = {
            },
        )
    }
}
