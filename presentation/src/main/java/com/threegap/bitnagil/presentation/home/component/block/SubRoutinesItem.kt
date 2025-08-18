package com.threegap.bitnagil.presentation.home.component.block

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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

@Composable
fun SubRoutinesItem(
    subRoutineNames: List<String>,
    subRoutineCompleteYn: List<Boolean>,
    onSubRoutineToggle: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val minSize = minOf(subRoutineNames.size, subRoutineCompleteYn.size)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        repeat(minSize) { index ->
            val subRoutineName = subRoutineNames[index]
            val isCompleted = subRoutineCompleteYn.getOrElse(index) { false }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableWithoutRipple { onSubRoutineToggle(index, !isCompleted) },
            ) {
                BitnagilIcon(
                    id = if (isCompleted) R.drawable.ic_check_circle else R.drawable.ic_check_default,
                    tint = null,
                    modifier = Modifier.size(24.dp),
                )

                Text(
                    text = subRoutineName,
                    style = BitnagilTheme.typography.body2Medium,
                    color = BitnagilTheme.colors.coolGray40,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SubRoutinesItemPreview() {
    SubRoutinesItem(
        subRoutineNames = listOf("물 마시기", "스트레칭하기", "심호흡하기"),
        subRoutineCompleteYn = listOf(true, false, true),
        onSubRoutineToggle = { _, _ -> },
    )
}
