package com.threegap.bitnagil.presentation.report.model

sealed interface ReportSideEffect {
    data object NavigateToBack : ReportSideEffect
    data object FocusOnContent : ReportSideEffect
}
