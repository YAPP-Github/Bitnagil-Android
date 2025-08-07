package com.threegap.bitnagil.presentation.recommendroutine

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.presentation.recommendroutine.component.atom.RecommendCategoryChip
import com.threegap.bitnagil.presentation.recommendroutine.component.block.EmotionRecommendRoutineButton
import com.threegap.bitnagil.presentation.recommendroutine.component.block.RecommendRoutineItem
import com.threegap.bitnagil.presentation.recommendroutine.component.template.RecommendLevelBottomSheet
import com.threegap.bitnagil.presentation.recommendroutine.model.RecommendRoutineIntent
import com.threegap.bitnagil.presentation.recommendroutine.model.RecommendRoutineState

@Composable
fun RecommendRoutineScreenContainer(
    viewmodel: RecommendRoutineViewModel = hiltViewModel(),
    navigateToEmotion: () -> Unit,
    navigateToRegisterRoutine: (String?) -> Unit,
) {
    val uiState by viewmodel.container.stateFlow.collectAsStateWithLifecycle()

    if (uiState.recommendLevelBottomSheetVisible) {
        RecommendLevelBottomSheet(
            selectedRecommendLevel = uiState.selectedRecommendLevel,
            onRecommendLevelSelected = { selectedLevel ->
                viewmodel.sendIntent(RecommendRoutineIntent.OnRecommendLevelSelected(selectedLevel))
            },
            onDismiss = {
                viewmodel.sendIntent(RecommendRoutineIntent.HideRecommendLevelBottomSheet)
            },
        )
    }

    RecommendRoutineScreen(
        uiState = uiState,
        onCategorySelected = { category ->
            viewmodel.sendIntent(RecommendRoutineIntent.OnCategorySelected(category))
        },
        onShowDifficultyBottomSheet = {
            viewmodel.sendIntent(RecommendRoutineIntent.ShowRecommendLevelBottomSheet)
        },
        onRecommendRoutineByEmotionClick = navigateToEmotion,
        onRegisterRoutineClick = navigateToRegisterRoutine,
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BitnagilTheme.colors.white)
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
                    categoryName = category.displayName,
                    isSelected = uiState.selectedCategory == category,
                    onCategorySelected = {
                        onCategorySelected(category)
                    },
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(start = 16.dp),
        ) {
            Text(
                text = "루틴 목록",
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.body1SemiBold,
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
                    text = uiState.selectedRecommendLevel?.displayName ?: "난이도 선택",
                    color = BitnagilTheme.colors.coolGray60,
                    style = BitnagilTheme.typography.body2Medium,
                    modifier = Modifier.padding(start = 10.dp),
                )

                BitnagilIcon(
                    id = R.drawable.ic_down_arrow,
                    tint = BitnagilTheme.colors.coolGray60,
                    modifier = Modifier
                        .padding(end = 13.dp)
                        .size(16.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                if (uiState.isDefaultCategory && uiState.emotionMarbleType == null) {
                    item {
                        EmotionRecommendRoutineButton(
                            onClick = onRecommendRoutineByEmotionClick,
                        )
                    }
                }
                items(
                    items = uiState.currentRoutines,
                    key = { "${it.id}_${it.name}" },
                ) { routine ->
                    RecommendRoutineItem(
                        routineName = routine.name,
                        routineDescription = routine.description,
                        onAddRoutineClick = { onRegisterRoutineClick(routine.id.toString()) },
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
        uiState = RecommendRoutineState(),
        onCategorySelected = {},
        onShowDifficultyBottomSheet = {},
        onRecommendRoutineByEmotionClick = {},
        onRegisterRoutineClick = {},
    )
}
