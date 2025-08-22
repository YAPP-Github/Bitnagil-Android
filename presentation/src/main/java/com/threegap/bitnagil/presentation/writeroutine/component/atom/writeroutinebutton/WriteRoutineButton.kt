package com.threegap.bitnagil.presentation.writeroutine.component.atom.writeroutinebutton

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple

class WriteRoutineButton {
    companion object {
        @Composable
        fun Text(
            text: String,
            isSelected: Boolean,
            onClick: () -> Unit,
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
        ) {
            val textColor = if (isSelected) BitnagilTheme.colors.white else BitnagilTheme.colors.coolGray30

            WriteRoutineButtonFrame(
                modifier = modifier,
                isSelected = isSelected,
                onClick = onClick,
                enabled = enabled,
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = text,
                        style = BitnagilTheme.typography.body2SemiBold.copy(color = textColor),
                    )
                }
            }
        }

        @Composable
        fun Custom(
            modifier: Modifier = Modifier,
            isSelected: Boolean,
            onClick: () -> Unit,
            enabled: Boolean = true,
            content: @Composable () -> Unit,
        ) {
            WriteRoutineButtonFrame(
                modifier = modifier,
                isSelected = isSelected,
                onClick = onClick,
                enabled = enabled,
            ) {
                content()
            }
        }
    }
}

@Composable
private fun WriteRoutineButtonFrame(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    val borderColor = if (isSelected) BitnagilTheme.colors.coolGray10 else BitnagilTheme.colors.coolGray97
    val backgroundColor = if (isSelected) BitnagilTheme.colors.coolGray10 else BitnagilTheme.colors.white

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp),
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp),
            )
            .clickableWithoutRipple(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Composable
@Preview(showBackground = true, widthDp = 300, heightDp = 300)
private fun WriteRoutineButtonPreview() {
    BitnagilTheme {
        Row {
            WriteRoutineButton.Text(
                text = "월",
                isSelected = true,
                onClick = {},
            )
            WriteRoutineButton.Text(
                text = "화",
                isSelected = false,
                onClick = {},
            )
        }
    }
}
