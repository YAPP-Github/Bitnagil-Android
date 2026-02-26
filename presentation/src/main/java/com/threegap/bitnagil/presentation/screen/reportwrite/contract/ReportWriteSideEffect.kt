package com.threegap.bitnagil.presentation.screen.reportwrite.contract

sealed interface ReportWriteSideEffect {
    data object NavigateToBack : ReportWriteSideEffect
    data object FocusOnContent : ReportWriteSideEffect
}
