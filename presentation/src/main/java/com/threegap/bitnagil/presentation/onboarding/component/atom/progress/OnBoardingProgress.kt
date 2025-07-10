import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun OnBoardingProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    height: Dp = 8.dp,
    gradientStartColor: Color = Color(0xFFADD8E6),
    gradientEndColor: Color = Color(0xFFFFB6C1),
    backgroundColor: Color = Color(0xFFE0E0E0),
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        label = "ProgressBarAnimation",
        animationSpec = tween(durationMillis = 100, easing = LinearEasing),
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
    ) {
        val strokeWidth = size.height

        drawLine(
            color = backgroundColor,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )

        if (animatedProgress > 0) {
            val progressEndX = size.width * animatedProgress
            drawLine(
                brush = Brush.horizontalGradient(
                    colors = listOf(gradientStartColor, gradientEndColor),
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
