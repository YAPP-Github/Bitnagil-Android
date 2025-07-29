package com.threegap.bitnagil.presentation.recommendroutine.component.template

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.presentation.recommendroutine.type.RecommendRoutineDifficulty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineDifficultyBottomSheet(
    selectedDifficulty: RecommendRoutineDifficulty?,
    onDifficultySelected: (RecommendRoutineDifficulty?) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
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
            RecommendRoutineDifficulty.entries.forEachIndexed { index, difficulty ->
                DifficultyOption(
                    optionText = difficulty.displayName,
                    isSelected = selectedDifficulty == difficulty,
                    onClick = {
                        val newDifficulty = if (selectedDifficulty == difficulty) null else difficulty
                        onDifficultySelected(newDifficulty)
                    },
                )

                if (index < RecommendRoutineDifficulty.entries.size - 1) {
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
private fun DifficultyOption(
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
            text = optionText,
            color = BitnagilTheme.colors.black,
            style = BitnagilTheme.typography.body1Regular,
            modifier = Modifier.weight(1f),
        )

        if (isSelected) {
            BitnagilIcon(
                id = R.drawable.ic_check,
                tint = BitnagilTheme.colors.orange500,
            )
        }
    }
}

@Preview
@Composable
private fun RoutineDifficultyBottomSheetPreview() {
    RoutineDifficultyBottomSheet(
        selectedDifficulty = null,
        onDifficultySelected = {},
        onDismiss = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun DifficultyOptionPreview() {
    DifficultyOption(
        optionText = "가볍게 할 수 있어요",
        isSelected = true,
        onClick = {},
    )
}
