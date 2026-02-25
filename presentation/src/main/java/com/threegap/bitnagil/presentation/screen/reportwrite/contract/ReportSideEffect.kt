package com.threegap.bitnagil.presentation.screen.reportwrite.contract

sealed interface ReportSideEffect {
    data object NavigateToBack : ReportSideEffect
    data object FocusOnContent : ReportSideEffect
}
