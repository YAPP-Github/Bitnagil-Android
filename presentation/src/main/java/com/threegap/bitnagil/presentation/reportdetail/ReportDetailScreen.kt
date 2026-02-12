package com.threegap.bitnagil.presentation.reportdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.presentation.reportdetail.component.atom.ReportProcessBadge
import com.threegap.bitnagil.presentation.reportdetail.component.block.ReportDetailLabeledContent
import com.threegap.bitnagil.presentation.reportdetail.contract.ReportDetailState
import com.threegap.bitnagil.presentation.reportdetail.util.toPresentationFormatInReportDetail
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ReportDetailScreenContainer(
    viewModel: ReportDetailViewModel,
    navigateToBack: () -> Unit,
) {
    val state by viewModel.collectAsState()

    ReportDetailScreen(
        state = state,
        onClickPreviousButton = navigateToBack,
    )
}

@Composable
private fun ReportDetailScreen(
    modifier: Modifier = Modifier,
    onClickPreviousButton: () -> Unit,
    state: ReportDetailState,
) {
    val verticalScrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BitnagilTheme.colors.white)
            .statusBarsPadding(),
    ) {
        BitnagilTopBar(
            title = "제보하기",
            showBackButton = true,
            onBackClick = onClickPreviousButton,
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(verticalScrollState)
                .padding(horizontal = 20.dp),
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            ReportProcessBadge(reportProcess = state.reportProcess)

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = state.date.toPresentationFormatInReportDetail(),
                style = BitnagilTheme.typography.subtitle1SemiBold,
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                state.imageUrls.forEach { imageUrl ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .build(),
                        modifier = Modifier
                            .size(74.dp)
                            .clip(shape = RoundedCornerShape(9.dp))
                            .background(color = BitnagilTheme.colors.black),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(28.dp),
            ) {
                ReportDetailLabeledContent(
                    label = "제목",
                    content = state.reportTitle,
                )

                ReportDetailLabeledContent(
                    label = "카테고리",
                    content = state.reportCategory.title,
                )

                ReportDetailLabeledContent(
                    label = "상세 제목 내용",
                    content = state.reportContent,
                )

                ReportDetailLabeledContent(
                    label = "내 위치",
                    content = state.location,
                )
            }

            Spacer(modifier = Modifier.height(36.dp))
        }
    }
}

@Composable
@Preview
private fun ReportDetailScreenPreview() {
    BitnagilTheme {
        ReportDetailScreen(
            state = ReportDetailState.Init.copy(
                reportContent = "Lorem ipsum dolor sit amet, " +
                    "consectetur adipiscing elit," +
                    " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                    "Nisl tincidunt eget nullam non.",
                imageUrls = listOf("", ""),
            ),
            onClickPreviousButton = {},
        )
    }
}
