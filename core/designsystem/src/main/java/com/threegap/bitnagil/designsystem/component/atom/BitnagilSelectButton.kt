package com.threegap.bitnagil.designsystem.component.atom

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R

@Composable
fun BitnagilSelectButton(
    title: String,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    titleTextStyle: TextStyle = BitnagilTheme.typography.subtitle1SemiBold,
    description: String? = null,
    selected: Boolean = false,
    colors: BitnagilSelectButtonColor = BitnagilSelectButtonColor.default(),
    shape: Shape = RoundedCornerShape(12.dp),
) {
    val interactionSource = remember(onClick) { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val iconAlpha by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = tween(200),
        label = "iconAlpha",
    )

    val backgroundColor = when {
        isPressed -> colors.pressedBackgroundColor
        selected -> colors.selectedBackgroundColor
        else -> colors.defaultBackgroundColor
    }

    val contentColor = when {
        isPressed -> colors.pressedContentColor
        selected -> colors.selectedContentColor
        else -> colors.defaultContentColor
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(backgroundColor)
            .let {
                if (onClick != null) {
                    it.clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onClick,
                    )
                } else {
                    it
                }
            }
            .padding(horizontal = 20.dp, vertical = 14.dp)
            .semantics {
                role = Role.Button
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = title,
                color = contentColor,
                style = titleTextStyle,
            )

            description?.let {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = description,
                    color = contentColor,
                    style = BitnagilTheme.typography.body2Medium,
                )
            }
        }

        BitnagilIcon(
            id = R.drawable.ic_check_circle_orange,
            tint = null,
            modifier = Modifier
                .size(28.dp)
                .alpha(iconAlpha),
        )
    }
}

@Immutable
data class BitnagilSelectButtonColor(
    val defaultBackgroundColor: Color,
    val selectedBackgroundColor: Color,
    val pressedBackgroundColor: Color,
    val defaultContentColor: Color,
    val selectedContentColor: Color,
    val pressedContentColor: Color,
) {
    companion object {
        @Composable
        fun default(): BitnagilSelectButtonColor = BitnagilSelectButtonColor(
            defaultBackgroundColor = BitnagilTheme.colors.coolGray99,
            selectedBackgroundColor = BitnagilTheme.colors.orange50,
            pressedBackgroundColor = BitnagilTheme.colors.orange50,
            defaultContentColor = BitnagilTheme.colors.coolGray50,
            selectedContentColor = BitnagilTheme.colors.orange500,
            pressedContentColor = BitnagilTheme.colors.orange500,
        )

        @Composable
        fun withdrawal(): BitnagilSelectButtonColor = BitnagilSelectButtonColor(
            defaultBackgroundColor = BitnagilTheme.colors.coolGray99,
            selectedBackgroundColor = BitnagilTheme.colors.orange50,
            pressedBackgroundColor = BitnagilTheme.colors.orange50,
            defaultContentColor = BitnagilTheme.colors.coolGray80,
            selectedContentColor = BitnagilTheme.colors.orange500,
            pressedContentColor = BitnagilTheme.colors.orange500,
        )
    }
}

@Preview
@Composable
private fun Preview() {
    Column {
        BitnagilSelectButton(
            title = "루틴명",
            onClick = {},
        )

        Spacer(modifier = Modifier.height(12.dp))

        BitnagilSelectButton(
            title = "루틴명",
            selected = true,
            onClick = {},
        )

        Spacer(modifier = Modifier.height(12.dp))

        BitnagilSelectButton(
            title = "루틴명",
            description = "세부 루틴 한 줄 설명",
            onClick = {},
        )

        Spacer(modifier = Modifier.height(12.dp))

        BitnagilSelectButton(
            title = "루틴명",
            description = "세부 루틴 한 줄 설명",
            selected = true,
            onClick = {},
        )
    }
}
