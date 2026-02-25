package com.threegap.bitnagil.presentation.screen.home.model

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.WindowInfo
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp

@Stable
internal class CollapsibleHeaderState(
    private val density: Density,
    val stickyHeaderHeightDp: Dp,
    val expandedHeaderHeightDp: Dp,
) {
    private val stickyHeaderHeightPx: Float = with(density) { stickyHeaderHeightDp.toPx() }

    private val expandedHeaderHeightPx: Float = with(density) { expandedHeaderHeightDp.toPx() }

    val collapsedContentOffsetDp: Dp = with(density) { stickyHeaderHeightPx.toDp() + 18.dp }

    var currentHeightPx by mutableFloatStateOf(expandedHeaderHeightPx)
        private set

    val expansionProgress: Float
        get() = if (expandedHeaderHeightPx > 0f) (currentHeightPx / expandedHeaderHeightPx).coerceIn(0f, 1f) else 1f

    val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource) =
            if (available.y < 0) consumeDelta(available.y) else Offset.Zero

        override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource) =
            if (available.y > 0) consumeDelta(available.y) else Offset.Zero

        override suspend fun onPreFling(available: Velocity): Velocity {
            if (currentHeightPx <= 0f || currentHeightPx >= expandedHeaderHeightPx) return Velocity.Zero

            val collapse = 0f
            val expand = expandedHeaderHeightPx

            val target = when {
                available.y < -50f -> collapse
                available.y > 50f -> expand
                else -> if (currentHeightPx - collapse < expand - currentHeightPx) collapse else expand
            }

            snapTo(targetHeight = target, velocity = available.y)

            return Velocity(0f, available.y)
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            if (available.y > 0 && currentHeightPx < expandedHeaderHeightPx) {
                snapTo(targetHeight = expandedHeaderHeightPx, velocity = available.y)
                return Velocity(0f, available.y)
            }

            if (available.y < 0 && currentHeightPx > 0f) {
                snapTo(targetHeight = 0f, velocity = available.y)
                return Velocity(0f, available.y)
            }

            return Velocity.Zero
        }
    }

    private fun consumeDelta(delta: Float): Offset {
        val oldHeight = currentHeightPx
        currentHeightPx = (oldHeight + delta).coerceIn(0f, expandedHeaderHeightPx)
        return Offset(0f, currentHeightPx - oldHeight)
    }

    private suspend fun snapTo(targetHeight: Float, velocity: Float) {
        if (currentHeightPx == targetHeight) return

        animate(
            initialValue = currentHeightPx,
            targetValue = targetHeight,
            initialVelocity = velocity,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessMediumLow,
            ),
        ) { value, _ ->
            currentHeightPx = value
        }
    }
}

@Composable
internal fun rememberCollapsibleHeaderState(
    density: Density = LocalDensity.current,
    windowInfo: WindowInfo = LocalWindowInfo.current,
    stickyHeaderHeight: Dp = 48.dp,
    minExpandedHeaderHeight: Dp = 146.dp,
): CollapsibleHeaderState {
    val containerSize = windowInfo.containerSize
    return remember(density, containerSize, minExpandedHeaderHeight, stickyHeaderHeight) {
        val screenHeightDp = with(density) { containerSize.height.toDp() }
        val expandedHeaderHeightDp = (screenHeightDp * 0.18f).coerceAtLeast(minExpandedHeaderHeight)

        CollapsibleHeaderState(
            density = density,
            stickyHeaderHeightDp = stickyHeaderHeight,
            expandedHeaderHeightDp = expandedHeaderHeightDp,
        )
    }
}
