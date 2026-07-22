package com.threegap.bitnagil.presentation.screen.summary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIconButton
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.presentation.screen.summary.component.template.summarybadge.SummaryBadgeView
import com.threegap.bitnagil.presentation.screen.summary.component.template.summarycalendar.SummaryCalendarView
import com.threegap.bitnagil.presentation.screen.summary.contract.SummaryState
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import java.time.YearMonth

@Composable
fun SummaryScreenContainer(
    viewModel: SummaryViewModel = hiltViewModel(),
    navigateToYouthPolicies: () -> Unit
) {
    val state by viewModel.collectAsState()

    SummaryScreen(
        state = state,
        onMonthChanged = viewModel::onMonthChanged,
        onClickYouthPolicies = navigateToYouthPolicies
    )
}

private const val INITIAL_PAGE = Int.MAX_VALUE / 2

@Composable
fun SummaryScreen(
    state: SummaryState,
    onMonthChanged: (YearMonth) -> Unit = {},
    onClickYouthPolicies: () -> Unit,
) {
    val verticalScrollState = rememberScrollState()
    val pagerState = rememberPagerState(initialPage = INITIAL_PAGE) { Int.MAX_VALUE }
    val scope = rememberCoroutineScope()

    // 페이지 변경 감지하여 ViewModel 업데이트
    LaunchedEffect(pagerState.currentPage) {
        val monthOffset = pagerState.currentPage - INITIAL_PAGE
        val targetMonth = YearMonth.now().plusMonths(monthOffset.toLong())
        if (state.currentMonth != targetMonth) {
            onMonthChanged(targetMonth)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BitnagilTheme.colors.white)
            .verticalScroll(verticalScrollState),
    ) {
        SummaryBadgeView(
            summaryBadge = state.currentMonthBadges,
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BitnagilTheme.colors.orange25)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_complaint),
                contentDescription = null
            )

            Text("청년 공고도 확인할 수 있어요!", style = BitnagilTheme.typography.body2SemiBold, modifier = Modifier.padding(start = 10.dp))

            Spacer(modifier = Modifier.weight(1f))

            Text("더보기", color = BitnagilTheme.colors.orange500, modifier = Modifier.clickableWithoutRipple(onClick = onClickYouthPolicies).padding(10.dp), style = BitnagilTheme.typography.body2SemiBold)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("감정 구슬 기록", style = BitnagilTheme.typography.title3SemiBold)
            Spacer(modifier = Modifier.weight(1f))
            BitnagilIconButton(
                id = R.drawable.ic_back_arrow_20,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                modifier = Modifier.size(48.dp)
            )
            Text(
                "${state.currentMonth.year}년 ${state.currentMonth.monthValue}월",
                style = BitnagilTheme.typography.subtitle1SemiBold
            )
            BitnagilIconButton(
                id = R.drawable.ic_right_arrow_20,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) { page ->
            val monthOffset = page - INITIAL_PAGE
            val displayMonth = YearMonth.now().plusMonths(monthOffset.toLong())

            SummaryCalendarView(
                yearMonth = displayMonth,
                emotionDays = state.emotionCellsOf(displayMonth),
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(showBackground = true, heightDp = 800)
@Composable
private fun SummaryScreenPreview() {
    BitnagilTheme {
        SummaryScreen(state = SummaryState.INIT, onClickYouthPolicies = {})
    }
}
