package com.threegap.bitnagil.presentation.reporthistory.component.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.presentation.reporthistory.model.ReportProcess

@Composable
fun ReportProcessBadge(
    modifier: Modifier = Modifier,
    reportProcess: ReportProcess,
) {
    Text(
        text = reportProcess.title,
        style = BitnagilTheme.typography.caption1SemiBold,
        color = reportProcess.getProcessBadgeTextColor(),
        modifier = modifier
            .background(color = reportProcess.getProcessBadgeBackgroundColor(), shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp),
    )
}

@Composable
private fun ReportProcess.getProcessBadgeBackgroundColor(): Color =
    when (this) {
        ReportProcess.Reported -> BitnagilTheme.colors.green10
        ReportProcess.Progress -> BitnagilTheme.colors.skyBlue10
        else -> BitnagilTheme.colors.coolGray95
    }

@Composable
private fun ReportProcess.getProcessBadgeTextColor(): Color =
    when (this) {
        ReportProcess.Reported -> BitnagilTheme.colors.green300
        ReportProcess.Progress -> BitnagilTheme.colors.blue300
        else -> BitnagilTheme.colors.coolGray40
    }

@Composable
@Preview
private fun ReportProcessBadgePreview() {
    BitnagilTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            ReportProcessBadge(reportProcess = ReportProcess.Progress)
            ReportProcessBadge(reportProcess = ReportProcess.Reported)
            ReportProcessBadge(reportProcess = ReportProcess.Complete)
        }
    }
}
