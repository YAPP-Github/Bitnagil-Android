package com.threegap.bitnagil.presentation.recommendroutine.component.template

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendLevel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendLevelBottomSheet(
    selectedRecommendLevel: RecommendLevel?,
    onRecommendLevelSelected: (RecommendLevel?) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        containerColor = BitnagilTheme.colors.white,
        contentColor = BitnagilTheme.colors.white,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(
                vertical = 24.dp,
                horizontal = 16.dp,
            ),
        ) {
            RecommendLevel.entries.forEachIndexed { index, recommendLevel ->
                LevelOption(
                    optionText = recommendLevel.displayName,
                    isSelected = selectedRecommendLevel == recommendLevel,
                    onClick = {
                        val newLevel = if (selectedRecommendLevel == recommendLevel) null else recommendLevel
                        onRecommendLevelSelected(newLevel)
                        coroutineScope
                            .launch { sheetState.hide() }
                            .invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    onDismiss()
                                }
                            }
                    },
                )

                if (index < RecommendLevel.entries.size - 1) {
                    HorizontalDivider(
                        color = BitnagilTheme.colors.coolGray97,
                        modifier = Modifier.padding(vertical = 8.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun LevelOption(
    optionText: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            // todo: 리플효과 제거하기
            .clickable { onClick() }
            .padding(vertical = 8.dp),
    ) {
        Text(
            text = optionText,
            color = BitnagilTheme.colors.black,
            style = BitnagilTheme.typography.body1Regular,
            modifier = Modifier.weight(1f),
        )

        if (isSelected) {
            // todo: 아이콘 변경하기
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = BitnagilTheme.colors.orange500,
            )
        }
    }
}

@Preview
@Composable
private fun RecommendLevelBottomSheetPreview() {
    RecommendLevelBottomSheet(
        selectedRecommendLevel = null,
        onRecommendLevelSelected = {},
        onDismiss = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun LevelOptionPreview() {
    LevelOption(
        optionText = "가볍게 할 수 있어요",
        isSelected = true,
        onClick = {},
    )
}
