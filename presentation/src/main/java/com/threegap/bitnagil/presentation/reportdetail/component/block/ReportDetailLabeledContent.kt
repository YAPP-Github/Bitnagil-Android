package com.threegap.bitnagil.presentation.reportdetail.component.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme

@Composable
fun ReportDetailLabeledContent(
    modifier: Modifier = Modifier,
    label: String,
    content: String,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = label,
            style = BitnagilTheme.typography.body2SemiBold,
            color = BitnagilTheme.colors.coolGray10,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BitnagilTheme.colors.coolGray99, shape = RoundedCornerShape(12.dp))
                .padding(vertical = 16.dp, horizontal = 20.dp),
            text = content,
            style = BitnagilTheme.typography.body2Medium,
        )
    }
}
