package com.threegap.bitnagil.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.presentation.screen.home.component.template.CollapsibleHeader
import com.threegap.bitnagil.presentation.screen.home.component.template.EmptyRoutineView
import com.threegap.bitnagil.presentation.screen.home.component.template.RoutineSection
import com.threegap.bitnagil.presentation.screen.home.component.template.StickyHeader
import com.threegap.bitnagil.presentation.screen.home.component.template.WeeklyDatePicker
import com.threegap.bitnagil.presentation.screen.home.contract.HomeSideEffect
import com.threegap.bitnagil.presentation.screen.home.contract.HomeState
import com.threegap.bitnagil.presentation.screen.home.model.DailyEmotionUiModel
import com.threegap.bitnagil.presentation.screen.home.model.rememberCollapsibleHeaderState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.time.LocalDate
import kotlin.math.pow

@Composable
fun HomeScreenContainer(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToGuide: () -> Unit,
    navigateToRegisterRoutine: () -> Unit,
    navigateToEmotion: () -> Unit,
    navigateToRoutineList: (String) -> Unit,
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is HomeSideEffect.NavigateToGuide -> navigateToGuide()
            is HomeSideEffect.NavigateToRegisterRoutine -> navigateToRegisterRoutine()
            is HomeSideEffect.NavigateToEmotion -> navigateToEmotion()
            is HomeSideEffect.NavigateToRoutineList -> navigateToRoutineList(sideEffect.selectedDate)
        }
    }

    HomeScreen(
        uiState = uiState,
        onDateSelect = viewModel::selectDate,
        onPreviousWeekClick = viewModel::getPreviousWeek,
        onNextWeekClick = viewModel::getNextWeek,
        onRoutineToggle = viewModel::toggleRoutine,
        onSubRoutineToggle = viewModel::toggleSubRoutine,
        onHelpClick = viewModel::navigateToGuide,
        onRegisterRoutineClick = viewModel::navigateToRegisterRoutine,
        onRegisterEmotionClick = viewModel::navigateToEmotion,
        onShowMoreRoutinesClick = viewModel::navigateToRoutineList,
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeState,
    modifier: Modifier = Modifier,
    onDateSelect: (LocalDate) -> Unit,
    onPreviousWeekClick: () -> Unit,
    onNextWeekClick: () -> Unit,
    onRoutineToggle: (String) -> Unit,
    onSubRoutineToggle: (String, Int) -> Unit,
    onHelpClick: () -> Unit,
    onRegisterRoutineClick: () -> Unit,
    onRegisterEmotionClick: () -> Unit,
    onShowMoreRoutinesClick: () -> Unit,
) {
    val collapsibleHeaderState = rememberCollapsibleHeaderState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BitnagilTheme.colors.coolGray10)
            .statusBarsPadding()
            .nestedScroll(collapsibleHeaderState.nestedScrollConnection),
    ) {
        StickyHeader(
            modifier = Modifier
                .padding(top = 14.dp)
                .height(collapsibleHeaderState.stickyHeaderHeightDp),
            onHelpClick = onHelpClick,
        )

        CollapsibleHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp, start = 18.dp, end = 18.dp)
                .height(collapsibleHeaderState.expandedHeaderHeightDp)
                .graphicsLayer { alpha = collapsibleHeaderState.expansionProgress.pow(3) },
            welcomeMessage = "${uiState.userNickname}${uiState.dailyEmotion.homeMessage}",
            dailyEmotion = uiState.dailyEmotion,
            onRegisterEmotionClick = onRegisterEmotionClick,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = collapsibleHeaderState.collapsedContentOffsetDp)
                .graphicsLayer { translationY = collapsibleHeaderState.currentHeightPx }
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(color = BitnagilTheme.colors.coolGray99),
        ) {
            WeeklyDatePicker(
                selectedDate = uiState.selectedDate,
                weeklyDates = uiState.currentWeeks,
                routines = uiState.routineSchedule,
                onDateSelect = onDateSelect,
                onPreviousWeekClick = onPreviousWeekClick,
                onNextWeekClick = onNextWeekClick,
            )

            if (uiState.selectedDateRoutines.isEmpty()) {
                EmptyRoutineView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 62.dp)
                        .verticalScroll(rememberScrollState()),
                    onRegisterRoutineClick = onRegisterRoutineClick,
                )
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(
                        text = "루틴 리스트",
                        color = BitnagilTheme.colors.coolGray60,
                        style = BitnagilTheme.typography.body2SemiBold,
                        modifier = Modifier.padding(top = 6.dp),
                    )
                    Text(
                        text = "더보기",
                        color = BitnagilTheme.colors.coolGray10,
                        style = BitnagilTheme.typography.body2SemiBold,
                        modifier = Modifier
                            .clickableWithoutRipple { onShowMoreRoutinesClick() }
                            .padding(vertical = 10.dp, horizontal = 12.dp),
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 48.dp),
                ) {
                    items(
                        items = uiState.selectedDateRoutines,
                        key = { routine -> routine.id },
                    ) { routine ->
                        RoutineSection(
                            routine = routine,
                            onRoutineToggle = { onRoutineToggle(routine.id) },
                            onSubRoutineToggle = { subRoutineIndex ->
                                onSubRoutineToggle(routine.id, subRoutineIndex)
                            },
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeState.Companion.INIT.copy(
            userNickname = "홍길동",
            dailyEmotion = DailyEmotionUiModel.Companion.INIT.copy(
                homeMessage = "님, 오셨군요!\n오늘 기분은 어떤가요?",
            ),
        ),
        onDateSelect = {},
        onPreviousWeekClick = {},
        onNextWeekClick = {},
        onRoutineToggle = { _ -> },
        onSubRoutineToggle = { _, _ -> },
        onHelpClick = {},
        onRegisterRoutineClick = {},
        onRegisterEmotionClick = {},
        onShowMoreRoutinesClick = {},
    )
}
