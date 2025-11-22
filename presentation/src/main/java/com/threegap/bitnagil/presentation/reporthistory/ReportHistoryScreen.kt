package com.threegap.bitnagil.presentation.reporthistory

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilChip
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.presentation.reporthistory.component.block.ReportHistoryItem
import com.threegap.bitnagil.presentation.reporthistory.component.template.ReportCategoryBottomSheet
import com.threegap.bitnagil.presentation.reporthistory.model.ReportCategory
import com.threegap.bitnagil.presentation.reporthistory.model.ReportHistoriesPerDayUiModel
import com.threegap.bitnagil.presentation.reporthistory.model.ReportHistoryState
import com.threegap.bitnagil.presentation.reporthistory.model.ReportHistoryUiModel
import com.threegap.bitnagil.presentation.reporthistory.model.ReportProcess
import com.threegap.bitnagil.presentation.reporthistory.util.toPresentationFormat
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ReportHistoryScreenContainer(
    viewModel: ReportHistoryViewModel = hiltViewModel(),
    navigateToBack: () -> Unit,
    navigateToReportDetail: (String) -> Unit,
) {
    val state by viewModel.collectAsState()

    if (state.showSelectReportCategoryBottomSheet) {
        ReportCategoryBottomSheet(
            selectedCategory = state.selectedReportCategory,
            onDismiss = viewModel::hideReportCategoryBottomSheet,
            onSelected = viewModel::selectReportCategory,
        )
    }

    ReportHistoryScreen(
        state = state,
        onClickPreviousButton = navigateToBack,
        onClickReportProcessChip = viewModel::selectReportProcess,
        onClickReportCategoryButton = viewModel::showReportCategoryBottomSheet,
        onClickReportItem = navigateToReportDetail,
    )
}

@Composable
private fun ReportHistoryScreen(
    modifier: Modifier = Modifier,
    state: ReportHistoryState,
    onClickPreviousButton: () -> Unit,
    onClickReportProcessChip: (ReportProcess) -> Unit,
    onClickReportCategoryButton: () -> Unit,
    onClickReportItem: (String) -> Unit,
) {
    val horizontalScreenState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BitnagilTheme.colors.coolGray99)
            .statusBarsPadding(),
    ) {
        BitnagilTopBar(
            title = "내 제보 기록",
            showBackButton = true,
            onBackClick = onClickPreviousButton,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.horizontalScroll(horizontalScreenState),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Spacer(modifier = Modifier.width(8.dp))

            state.reportProcessWithCounts.forEach { reportProcessWithCount ->
                BitnagilChip(
                    title = reportProcessWithCount.process.title,
                    isSelected = state.selectedReportProcess == reportProcessWithCount.process,
                    onCategorySelected = {
                        onClickReportProcessChip(reportProcessWithCount.process)
                    },
                    count = if (reportProcessWithCount.count == 0) null else reportProcessWithCount.count,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            if (state.filteredReportHistoriesPerDays.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                ) {
                    state.filteredReportHistoriesPerDays.forEach { reportHistoriesPerDay ->
                        stickyHeader {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .background(color = BitnagilTheme.colors.coolGray99),
                            ) {
                                Text(
                                    text = reportHistoriesPerDay.date.toPresentationFormat(),
                                    modifier = Modifier.align(Alignment.CenterStart),
                                    style = BitnagilTheme.typography.body2SemiBold,
                                )
                            }
                        }

                        itemsIndexed(reportHistoriesPerDay.reports) { index, report ->
                            ReportHistoryItem(
                                modifier = Modifier.padding(bottom = if (index == reportHistoriesPerDay.reports.lastIndex) 24.dp else 10.dp),
                                report = report,
                                onClick = {
                                    onClickReportItem(report.id)
                                },
                            )
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = "제보한 내역이 없어요.", style = BitnagilTheme.typography.subtitle1SemiBold)
                    Text(text = "원하는 카테고리로 제보를 시작해 보세요.", style = BitnagilTheme.typography.body2Regular, color = BitnagilTheme.colors.coolGray70)
                }
            }

            if (state.showCategorySelectButton) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier
                        .height(40.dp)
                        .align(Alignment.TopEnd)
                        .clickableWithoutRipple(onClick = onClickReportCategoryButton),
                ) {
                    Text(
                        text = state.selectedReportCategory?.title ?: "카테고리",
                        color = BitnagilTheme.colors.coolGray40,
                        style = BitnagilTheme.typography.body2Medium,
                        modifier = Modifier.padding(start = 10.dp),
                    )

                    BitnagilIcon(
                        id = R.drawable.ic_down_arrow,
                        tint = BitnagilTheme.colors.coolGray40,
                        modifier = Modifier
                            .padding(end = 13.dp)
                            .size(16.dp),
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun ReportHistoryScreenPreview() {
    BitnagilTheme {
        ReportHistoryScreen(
            state = ReportHistoryState.Init.copy(
                reportHistoriesPerDays = List(10) {
                    ReportHistoriesPerDayUiModel(
                        date = java.time.LocalDate.now(),
                        reports = listOf(
                            ReportHistoryUiModel(
                                id = "1",
                                title = "제보 1",
                                imageUrl = "-",
                                location = "서울특별시 성북구 안암로 106",
                                process = ReportProcess.Reported,
                                category = ReportCategory.Amenities,
                            ),
                            ReportHistoryUiModel(
                                id = "1",
                                title = "제보 1",
                                imageUrl = "-",
                                location = "서울특별시 성북구 안암로 106",
                                process = ReportProcess.Progress,
                                category = ReportCategory.Amenities,
                            ),
                        ),
                    )
                },
            ),
            onClickPreviousButton = {},
            onClickReportProcessChip = {},
            onClickReportCategoryButton = {},
            onClickReportItem = {},
        )
    }
}
