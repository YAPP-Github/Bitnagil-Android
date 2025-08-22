package com.threegap.bitnagil.presentation.home.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.unit.dp

@Stable
data class CollapsibleHeaderState(
    val lazyListState: LazyListState,
    val nestedScrollConnection: NestedScrollConnection,
    val currentHeaderHeight: Dp,
    val collapseProgress: Float,
    val isFullyCollapsed: Boolean,
    val screenHeight: Dp,
    val screenWidth: Dp,
    val collapsedHeaderHeight: Dp,
)

@Composable
internal fun rememberCollapsibleHeaderState(): CollapsibleHeaderState {
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current

    val (screenHeight, screenWidth) = rememberScreenSize(windowInfo, density)
    val (expandedHeight, collapsedHeight, collapseRange) = rememberHeaderSizes(screenHeight)
    val scrollState = rememberScrollBehavior(expandedHeight, collapsedHeight, collapseRange, density)

    return CollapsibleHeaderState(
        lazyListState = scrollState.lazyListState,
        nestedScrollConnection = scrollState.nestedScrollConnection,
        currentHeaderHeight = scrollState.currentHeaderHeight,
        collapseProgress = scrollState.collapseProgress,
        isFullyCollapsed = scrollState.isFullyCollapsed,
        screenHeight = screenHeight,
        screenWidth = screenWidth,
        collapsedHeaderHeight = collapsedHeight,
    )
}

@Composable
private fun rememberScreenSize(windowInfo: WindowInfo, density: Density): Pair<Dp, Dp> {
    return remember(windowInfo.containerSize) {
        with(density) {
            windowInfo.containerSize.height.toDp() to windowInfo.containerSize.width.toDp()
        }
    }
}

@Composable
private fun rememberHeaderSizes(screenHeight: Dp): Triple<Dp, Dp, Dp> {
    return remember(screenHeight) {
        val expanded = screenHeight * EXPANDED_HEADER_RATIO
        val collapsed = screenHeight * COLLAPSED_HEADER_RATIO
        val range = expanded - collapsed
        Triple(expanded, collapsed, range)
    }
}

@Stable
private data class ScrollBehaviorState(
    val lazyListState: LazyListState,
    val nestedScrollConnection: NestedScrollConnection,
    val currentHeaderHeight: Dp,
    val collapseProgress: Float,
    val isFullyCollapsed: Boolean,
)

@Composable
private fun rememberScrollBehavior(
    expandedHeaderHeight: Dp,
    collapsedHeaderHeight: Dp,
    collapseRange: Dp,
    density: Density,
): ScrollBehaviorState {
    val lazyListState = rememberLazyListState()
    var scrollOffset by remember { mutableFloatStateOf(0f) }

    val collapseRangePx = remember(collapseRange, density) {
        with(density) { collapseRange.toPx() }
    }
    val bufferDistancePx = remember(density) {
        with(density) { SCROLL_BUFFER_DISTANCE.toPx() }
    }
    val maxScrollOffsetPx = remember(collapseRangePx, bufferDistancePx) {
        collapseRangePx + bufferDistancePx
    }

    val isFullyCollapsed by remember {
        derivedStateOf { scrollOffset <= -maxScrollOffsetPx }
    }

    val nestedScrollConnection = remember(lazyListState, maxScrollOffsetPx, collapseRangePx, scrollOffset) {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val deltaY = available.y

                return when {
                    deltaY < 0 -> {
                        if (scrollOffset > -maxScrollOffsetPx) {
                            val newOffset = (scrollOffset + deltaY).coerceAtLeast(-maxScrollOffsetPx)
                            val consumedOffset = newOffset - scrollOffset
                            scrollOffset = newOffset
                            Offset(0f, consumedOffset)
                        } else {
                            Offset.Zero
                        }
                    }

                    deltaY > 0 -> {
                        if (scrollOffset < 0f && isScrollAtTop(lazyListState)) {
                            val newOffset = (scrollOffset + deltaY).coerceAtMost(0f)
                            val consumedOffset = newOffset - scrollOffset
                            scrollOffset = newOffset
                            Offset(0f, consumedOffset)
                        } else {
                            Offset.Zero
                        }
                    }

                    else -> Offset.Zero
                }
            }
        }
    }

    val currentHeaderHeight by remember {
        derivedStateOf {
            val progress = (-scrollOffset / collapseRangePx).coerceIn(0f, 1f)
            expandedHeaderHeight - (collapseRange * progress)
        }
    }

    val collapseProgress by remember {
        derivedStateOf {
            (-scrollOffset / collapseRangePx).coerceIn(0f, 1f)
        }
    }

    return ScrollBehaviorState(
        lazyListState = lazyListState,
        nestedScrollConnection = nestedScrollConnection,
        currentHeaderHeight = currentHeaderHeight,
        collapseProgress = collapseProgress,
        isFullyCollapsed = isFullyCollapsed,
    )
}

private fun isScrollAtTop(lazyListState: LazyListState): Boolean =
    lazyListState.firstVisibleItemIndex == 0 && lazyListState.firstVisibleItemScrollOffset == 0

private const val EXPANDED_HEADER_RATIO = 225f / 722f
private const val COLLAPSED_HEADER_RATIO = 64f / 722f
private val SCROLL_BUFFER_DISTANCE = 30.dp
