package com.threegap.bitnagil.presentation.screen.reportdetail.component.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.domain.report.model.ReportStatus
import com.threegap.bitnagil.presentation.model.report.badgeBackgroundColor
import com.threegap.bitnagil.presentation.model.report.displayTitle
import com.threegap.bitnagil.presentation.model.report.textColor

@Composable
fun ReportProcessBadge(
    modifier: Modifier = Modifier,
    reportStatus: ReportStatus,
) {
    Text(
        text = reportStatus.displayTitle,
        style = BitnagilTheme.typography.caption1SemiBold,
        color = reportStatus.textColor,
        modifier = modifier
            .background(
                color = reportStatus.badgeBackgroundColor,
                shape = RoundedCornerShape(6.dp),
            )
            .padding(horizontal = 10.dp, vertical = 4.dp),
    )
}

@Composable
@Preview
private fun ReportProcessBadgePreview() {
    BitnagilTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            ReportProcessBadge(reportStatus = ReportStatus.PENDING)
            ReportProcessBadge(reportStatus = ReportStatus.IN_PROGRESS)
            ReportProcessBadge(reportStatus = ReportStatus.COMPLETED)
        }
    }
}
