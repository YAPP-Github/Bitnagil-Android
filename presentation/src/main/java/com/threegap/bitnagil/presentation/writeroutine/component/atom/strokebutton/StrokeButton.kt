package com.threegap.bitnagil.presentation.writeroutine.component.atom.strokebutton

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme

class StrokeButton {
    companion object {
        @Composable
        fun Text(
            text: String,
            isSelected: Boolean,
            onClick: () -> Unit,
            modifier: Modifier = Modifier,
            enabled: Boolean = true
        ) {
            val textColor = if (isSelected) BitnagilTheme.colors.navy500 else BitnagilTheme.colors.navy100

            StrokeButtonFrame(
                modifier = modifier,
                isSelected = isSelected,
                onClick = onClick,
                enabled = enabled,
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text,
                        style = BitnagilTheme.typography.body2SemiBold.copy(color = textColor)
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
            content: @Composable () -> Unit
        ) {
            StrokeButtonFrame(
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
private fun StrokeButtonFrame(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    val borderColor = if (isSelected) BitnagilTheme.colors.navy500 else BitnagilTheme.colors.navy100

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
