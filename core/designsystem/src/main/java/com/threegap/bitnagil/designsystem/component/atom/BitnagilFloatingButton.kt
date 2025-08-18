package com.threegap.bitnagil.designsystem.component.atom

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple

@Composable
fun BitnagilFloatingButton(
    @DrawableRes id: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    colors: BitnagilFloatingButtonColor = BitnagilFloatingButtonColor.default(),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                color = if (isActive) colors.activeIconBackgroundColor else colors.defaultIconBackgroundColor,
                shape = CircleShape,
            )
            .size(52.dp)
            .clickableWithoutRipple { onClick() },
    ) {
        BitnagilIcon(
            id = id,
            tint = if (isActive) colors.activeIconColor else colors.defaultIconColor,
        )
    }
}

@Composable
fun BitnagilFloatingActionMenu(
    actions: List<FloatingActionItem>,
    isExpanded: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes defaultIcon: Int = R.drawable.ic_add,
    @DrawableRes activeIcon: Int = R.drawable.ic_close,
    colors: BitnagilFloatingButtonColor = BitnagilFloatingButtonColor.default(),
) {
    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = isExpanded && actions.isNotEmpty(),
            enter = slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = tween(durationMillis = 300),
            ) + fadeIn(
                animationSpec = tween(durationMillis = 300),
            ) + scaleIn(
                initialScale = 0.8f,
                animationSpec = tween(durationMillis = 300),
            ),
            exit = slideOutVertically(
                targetOffsetY = { it / 2 },
                animationSpec = tween(durationMillis = 200),
            ) + fadeOut(
                animationSpec = tween(durationMillis = 200),
            ) + scaleOut(
                targetScale = 0.8f,
                animationSpec = tween(durationMillis = 200),
            ),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 67.dp),
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = BitnagilTheme.colors.white,
                        shape = RoundedCornerShape(12.dp),
                    ),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    actions.forEach { action ->
                        FloatingActionMenuItem(
                            icon = action.icon,
                            text = action.text,
                            onClick = {
                                action.onClick()
                                onToggle(false)
                            },
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier.align(Alignment.BottomEnd),
        ) {
            BitnagilFloatingButton(
                id = if (isExpanded) activeIcon else defaultIcon,
                onClick = { onToggle(!isExpanded) },
                isActive = isExpanded,
                colors = colors,
            )
        }
    }
}

data class FloatingActionItem(
    @DrawableRes val icon: Int,
    val text: String,
    val onClick: () -> Unit,
)

@Immutable
data class BitnagilFloatingButtonColor(
    val defaultIconColor: Color,
    val defaultIconBackgroundColor: Color,
    val activeIconColor: Color,
    val activeIconBackgroundColor: Color,
) {
    companion object {
        @Composable
        fun default() = BitnagilFloatingButtonColor(
            defaultIconColor = BitnagilTheme.colors.white,
            defaultIconBackgroundColor = BitnagilTheme.colors.orange500,
            activeIconColor = BitnagilTheme.colors.coolGray30,
            activeIconBackgroundColor = BitnagilTheme.colors.white,
        )
    }
}

@Composable
private fun FloatingActionMenuItem(
    @DrawableRes icon: Int,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 200),
        label = "menu_item_scale",
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        modifier = modifier
            .scale(scale)
            .clickableWithoutRipple { onClick() },
    ) {
        BitnagilIcon(
            id = icon,
            tint = null,
            modifier = Modifier.size(24.dp),
        )

        Text(
            text = text,
            style = BitnagilTheme.typography.body2Medium,
            color = BitnagilTheme.colors.coolGray30,
        )
    }
}

@Preview
@Composable
private fun BitnagilFloatingButtonPreview() {
    Column {
        BitnagilFloatingButton(
            id = R.drawable.ic_add,
            onClick = {},
        )

        BitnagilFloatingActionMenu(
            actions = listOf(
                FloatingActionItem(
                    icon = R.drawable.ic_routine_add,
                    text = "루틴 등록",
                    onClick = {},
                ),
            ),
            isExpanded = true,
            onToggle = {},
        )
    }
}
