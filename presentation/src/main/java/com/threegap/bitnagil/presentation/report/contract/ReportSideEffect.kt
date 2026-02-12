package com.threegap.bitnagil.presentation.report.contract

sealed interface ReportSideEffect {
    data object NavigateToBack : ReportSideEffect
    data object FocusOnContent : ReportSideEffect
}
