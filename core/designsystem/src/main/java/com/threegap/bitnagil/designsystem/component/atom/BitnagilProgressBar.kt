package com.threegap.bitnagil.designsystem.component.atom

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme

@Composable
fun BitnagilProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    color: BitnagilProgressBarColor = BitnagilProgressBarColor.default(),
    height: Dp = 5.dp,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        label = "ProgressBarAnimation",
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
    ) {
        val strokeWidth = size.height

        drawLine(
            color = color.backgroundColor,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )

        if (animatedProgress > 0) {
            val progressEndX = size.width * animatedProgress
            drawLine(
                brush = Brush.horizontalGradient(
                    colors = listOf(color.gradientStartColor, color.gradientEndColor),
                    startX = 0f,
                    endX = progressEndX,
                ),
                start = Offset(0f, size.height / 2),
                end = Offset(progressEndX, size.height / 2),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round,
            )
        }
    }
}

@Immutable
data class BitnagilProgressBarColor(
    val gradientStartColor: Color,
    val gradientEndColor: Color,
    val backgroundColor: Color
) {
    companion object {
        @Composable
        fun default(): BitnagilProgressBarColor = BitnagilProgressBarColor(
            gradientStartColor = BitnagilTheme.colors.progressBarGradientStartColor,
            gradientEndColor = BitnagilTheme.colors.progressBarGradientEndColor,
            backgroundColor = BitnagilTheme.colors.white
        )
    }
}

@Preview
@Composable
private fun BitnagilProgressBarPreview() {
    var progress by remember { mutableFloatStateOf(0.0f) }

    Column {
        BitnagilProgressBar(
            progress = progress,
            modifier = Modifier.padding(8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "down",
                modifier = Modifier.clickable {
                    progress = 0f
                },
            )

            Text(
                text = "up",
                modifier = Modifier.clickable {
                    progress += 0.1f
                },
            )
        }
    }
}
