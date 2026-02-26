package com.threegap.bitnagil.presentation.model.report

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.domain.report.model.ReportStatus

val ReportStatus.displayTitle: String
    get() = when (this) {
        ReportStatus.PENDING -> "제보 완료"
        ReportStatus.IN_PROGRESS -> "처리 중"
        ReportStatus.COMPLETED -> "처리 완료"
    }

val ReportStatus.badgeBackgroundColor: Color
    @Composable get() = when (this) {
        ReportStatus.PENDING -> BitnagilTheme.colors.green10
        ReportStatus.IN_PROGRESS -> BitnagilTheme.colors.skyBlue10
        else -> BitnagilTheme.colors.coolGray95
    }

val ReportStatus.textColor: Color
    @Composable get() = when (this) {
        ReportStatus.PENDING -> BitnagilTheme.colors.green300
        ReportStatus.IN_PROGRESS -> BitnagilTheme.colors.blue300
        else -> BitnagilTheme.colors.coolGray40
    }
