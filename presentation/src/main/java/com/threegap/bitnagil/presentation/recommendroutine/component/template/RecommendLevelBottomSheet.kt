package com.threegap.bitnagil.presentation.recommendroutine.component.template

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
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
                    optionLevel = recommendLevel.koreanLevel,
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
    optionLevel: String,
    optionText: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickableWithoutRipple { onClick() }
            .padding(vertical = 8.dp),
    ) {
        Text(
            text = "난이도 $optionLevel",
            color = BitnagilTheme.colors.coolGray10,
            style = BitnagilTheme.typography.body1SemiBold,
        )

        Text(
            text = " | $optionText",
            color = BitnagilTheme.colors.coolGray10,
            style = BitnagilTheme.typography.body1Regular,
            modifier = Modifier.weight(1f),
        )

        if (isSelected) {
            BitnagilIcon(
                id = R.drawable.ic_check_md,
                tint = BitnagilTheme.colors.orange500,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LevelOptionPreview() {
    LevelOption(
        optionLevel = "상",
        optionText = "가볍게 할 수 있어요",
        isSelected = true,
        onClick = {},
    )
}
