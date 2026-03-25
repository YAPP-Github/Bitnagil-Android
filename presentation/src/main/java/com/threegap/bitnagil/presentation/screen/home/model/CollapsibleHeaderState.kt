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
    val initialStickyHeaderHeightDp: Dp,
    val expandedHeaderHeightDp: Dp,
) {
    private val expandedHeaderHeightPx: Float = with(density) { expandedHeaderHeightDp.toPx() }

    var stickyHeaderActualBottomPx by mutableFloatStateOf(0f)
        internal set

    val collapsedContentOffsetDp: Dp
        get() = with(density) { stickyHeaderActualBottomPx.toDp() }

    var currentHeightPx by mutableFloatStateOf(expandedHeaderHeightPx)
        private set

    val expansionProgress: Float
        get() = (currentHeightPx / expandedHeaderHeightPx).coerceIn(0f, 1f)

    val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource) =
            if (available.y < 0) consumeDelta(available.y) else Offset.Zero

        override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource) =
            if (available.y > 0) consumeDelta(available.y) else Offset.Zero

        override suspend fun onPreFling(available: Velocity): Velocity {
            val isFullyCollapsed = currentHeightPx < 1f
            val isFullyExpanded = currentHeightPx > expandedHeaderHeightPx - 1f

            if (isFullyCollapsed || isFullyExpanded) return Velocity.Zero

            val target = when {
                available.y < -50f -> 0f
                available.y > 50f -> expandedHeaderHeightPx
                else -> if (currentHeightPx < expandedHeaderHeightPx / 2) 0f else expandedHeaderHeightPx
            }

            snapTo(targetHeight = target, velocity = available.y)
            return Velocity.Zero
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
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
        ) { value, _ -> currentHeightPx = value.coerceIn(0f, expandedHeaderHeightPx) }
    }
}

@Composable
internal fun rememberCollapsibleHeaderState(
    density: Density = LocalDensity.current,
    windowInfo: WindowInfo = LocalWindowInfo.current,
    initialStickyHeaderHeightDp: Dp = 48.dp,
    minExpandedHeaderHeight: Dp = 146.dp,
): CollapsibleHeaderState {
    val containerSize = windowInfo.containerSize
    return remember(density, containerSize, minExpandedHeaderHeight, initialStickyHeaderHeightDp) {
        val screenHeightDp = with(density) { containerSize.height.toDp() }
        val expandedHeaderHeightDp = (screenHeightDp * 0.18f).coerceAtLeast(minExpandedHeaderHeight)

        CollapsibleHeaderState(
            density = density,
            initialStickyHeaderHeightDp = initialStickyHeaderHeightDp,
            expandedHeaderHeightDp = expandedHeaderHeightDp,
        )
    }
}
