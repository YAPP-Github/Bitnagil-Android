package com.threegap.bitnagil.presentation.screen.reportwrite.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme

@Composable
fun ReportField(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = title,
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.body2SemiBold,
            )
            Text(
                text = "*",
                color = BitnagilTheme.colors.error10,
                style = BitnagilTheme.typography.body1SemiBold,
            )
        }
        content()
    }
}

@Preview
@Composable
private fun Preview() {
    ReportField(title = "제보 카테고리") {}
}
