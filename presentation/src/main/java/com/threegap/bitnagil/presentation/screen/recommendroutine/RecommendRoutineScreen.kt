package com.threegap.bitnagil.presentation.screen.recommendroutine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.presentation.common.extension.displayLevel
import com.threegap.bitnagil.presentation.common.extension.displayTitle
import com.threegap.bitnagil.presentation.common.extension.isVisible
import com.threegap.bitnagil.presentation.screen.recommendroutine.component.atom.RecommendCategoryChip
import com.threegap.bitnagil.presentation.screen.recommendroutine.component.block.EmotionRecommendRoutineButton
import com.threegap.bitnagil.presentation.screen.recommendroutine.component.block.RecommendRoutineItem
import com.threegap.bitnagil.presentation.screen.recommendroutine.component.template.EmptyRecommendRoutineView
import com.threegap.bitnagil.presentation.screen.recommendroutine.component.template.RecommendLevelBottomSheet
import com.threegap.bitnagil.presentation.screen.recommendroutine.contract.RecommendRoutineSideEffect
import com.threegap.bitnagil.presentation.screen.recommendroutine.contract.RecommendRoutineState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun RecommendRoutineScreenContainer(
    viewModel: RecommendRoutineViewModel = hiltViewModel(),
    navigateToEmotion: () -> Unit,
    navigateToRegisterRoutine: (String?) -> Unit,
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is RecommendRoutineSideEffect.NavigateToEmotion -> navigateToEmotion()
            is RecommendRoutineSideEffect.NavigateToRegisterRoutine -> navigateToRegisterRoutine(sideEffect.routineId)
        }
    }

    if (uiState.recommendLevelBottomSheetVisible) {
        RecommendLevelBottomSheet(
            selectedRecommendLevel = uiState.selectedRecommendLevel,
            onRecommendLevelSelected = viewModel::updateRecommendLevel,
            onDismiss = viewModel::hideRecommendLevelBottomSheet,
        )
    }

    RecommendRoutineScreen(
        uiState = uiState,
        onCategorySelected = viewModel::updateRoutineCategory,
        onShowDifficultyBottomSheet = viewModel::showRecommendLevelBottomSheet,
        onRecommendRoutineByEmotionClick = viewModel::navigateToEmotion,
        onRegisterRoutineClick = viewModel::navigateToRegisterRoutine,
    )
}

@Composable
private fun RecommendRoutineScreen(
    uiState: RecommendRoutineState,
    onCategorySelected: (RecommendCategory) -> Unit,
    onShowDifficultyBottomSheet: () -> Unit,
    onRecommendRoutineByEmotionClick: () -> Unit,
    onRegisterRoutineClick: (String) -> Unit,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(uiState.selectedCategory) {
        if (listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 0) {
            listState.animateScrollToItem(0)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BitnagilTheme.colors.coolGray99)
            .statusBarsPadding(),
    ) {
        BitnagilTopBar(title = "추천 루틴")

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            items(
                items = RecommendCategory.entries.filter { it.isVisible },
                key = { it.name },
            ) { category ->
                RecommendCategoryChip(
                    categoryName = category.displayTitle,
                    isSelected = uiState.selectedCategory == category,
                    onCategorySelected = {
                        onCategorySelected(category)
                    },
                )
            }
        }

        if (uiState.shouldShowEmotionButton) {
            Spacer(modifier = Modifier.height(20.dp))

            EmotionRecommendRoutineButton(
                onClick = onRecommendRoutineByEmotionClick,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(start = 16.dp),
        ) {
            Text(
                text = "추천 루틴리스트",
                color = BitnagilTheme.colors.coolGray60,
                style = BitnagilTheme.typography.body2SemiBold,
                modifier = Modifier.weight(1f),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .clickableWithoutRipple { onShowDifficultyBottomSheet() },
            ) {
                Text(
                    text = "난이도 ${uiState.selectedRecommendLevel?.displayLevel ?: "선택"}",
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

        Spacer(modifier = Modifier.height(12.dp))

        if (uiState.currentRoutines.isEmpty() && uiState.selectedRecommendLevel != null) {
            EmptyRecommendRoutineView(
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            ) {
                items(
                    items = uiState.currentRoutines,
                    key = { it.id },
                ) { routine ->
                    RecommendRoutineItem(
                        routine = routine,
                        onAddRoutineClick = {
                            // todo 추천 카테고리 같이 넘겨주기
                            onRegisterRoutineClick(routine.id.toString())
                        },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RoutineRecommendScreenPreview() {
    RecommendRoutineScreen(
        uiState = RecommendRoutineState.INIT,
        onCategorySelected = {},
        onShowDifficultyBottomSheet = {},
        onRecommendRoutineByEmotionClick = {},
        onRegisterRoutineClick = {},
    )
}
