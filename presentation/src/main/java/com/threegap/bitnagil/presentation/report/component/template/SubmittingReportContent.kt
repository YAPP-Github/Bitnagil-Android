package com.threegap.bitnagil.presentation.report.component.template

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon

@Composable
fun SubmittingReportContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BitnagilIcon(
            id = R.drawable.ic_loading,
            tint = null,
            modifier = Modifier.padding(bottom = 12.dp),
        )

        Text(
            text = "제보중...",
            color = BitnagilTheme.colors.coolGray10,
            style = BitnagilTheme.typography.title2Bold,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        Text(
            text = "포모가 열심\n제보하고 있어요",
            color = BitnagilTheme.colors.coolGray40,
            style = BitnagilTheme.typography.body1Medium,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(72.dp))

        Image(
            painter = painterResource(R.drawable.img_pomo_loading),
            contentDescription = null,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SubmittingReportContent()
}
