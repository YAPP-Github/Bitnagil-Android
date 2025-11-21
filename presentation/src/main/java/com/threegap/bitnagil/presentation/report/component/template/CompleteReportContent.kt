package com.threegap.bitnagil.presentation.report.component.template

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButtonColor
import com.threegap.bitnagil.presentation.report.ReportState
import com.threegap.bitnagil.presentation.report.component.CompleteReportCard

@Composable
fun CompleteReportContent(
    uiState: ReportState,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BitnagilIcon(
            id = R.drawable.ic_check_circle_orange,
            tint = null,
            modifier = Modifier
                .padding(bottom = 12.dp)
                .size(40.dp)
        )

        Text(
            text = "제보가 완료되었습니다.",
            color = BitnagilTheme.colors.coolGray10,
            style = BitnagilTheme.typography.title2Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "빛나길에서 접수 후 완료되면\n신고를 진행합니다.",
            color = BitnagilTheme.colors.coolGray40,
            style = BitnagilTheme.typography.body1Medium,
            textAlign = TextAlign.Center
        )

        Box(
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = painterResource(R.drawable.onboarding_character),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 34.dp)
                    .size(134.dp, 148.dp)
            )

            CompleteReportCard(
                modifier = Modifier
                    .offset(y = 115.dp)
                    .padding(horizontal = 18.dp),
                title = uiState.reportTitle,
                category = uiState.selectedCategory,
                address = uiState.currentAddress,
                content = uiState.reportContent,
                images = uiState.uploadedImagePaths,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        BitnagilTextButton(
            text = "확인",
            onClick = onConfirmClick,
            colors = BitnagilTextButtonColor.default(
                disabledBackgroundColor = BitnagilTheme.colors.coolGray98,
                disabledTextColor = BitnagilTheme.colors.coolGray90,
            ),
            enabled = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CompleteReportContent(
        uiState = ReportState.Init,
        onConfirmClick = {}
    )
}
