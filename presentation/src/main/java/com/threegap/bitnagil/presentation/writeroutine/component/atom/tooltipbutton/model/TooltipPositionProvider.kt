package com.threegap.bitnagil.presentation.writeroutine.component.atom.tooltipbutton.model

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.PopupPositionProvider

class CustomTooltipPositionProvider(
    private val direction: TooltipDirection,
    private val addPositionX: Int = 0
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        val x: Int
        val y: Int

        when (direction) {
            TooltipDirection.Center -> {
                x = anchorBounds.left + (anchorBounds.width / 2) - (popupContentSize.width / 2)
                y = anchorBounds.top - popupContentSize.height
            }
            TooltipDirection.Left -> {
                x = anchorBounds.left - addPositionX
                y = anchorBounds.top - popupContentSize.height
            }
            TooltipDirection.Right -> {
                x = anchorBounds.right + addPositionX
                y = anchorBounds.top - popupContentSize.height
            }
        }
        return IntOffset(x, y)
    }
}
