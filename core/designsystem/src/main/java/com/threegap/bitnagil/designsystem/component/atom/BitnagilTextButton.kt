package com.threegap.bitnagil.designsystem.component.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme

@Composable
fun BitnagilTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: BitnagilTextButtonColor = BitnagilTextButtonColor.default(),
    shape: Shape = RoundedCornerShape(12.dp),
    textStyle: TextStyle = BitnagilTheme.typography.body1SemiBold,
    textDecoration: TextDecoration? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor = when {
        !enabled -> colors.disabledBackgroundColor
        isPressed -> colors.pressedBackgroundColor
        else -> colors.defaultBackgroundColor
    }

    val textColor = when {
        !enabled -> colors.disabledTextColor
        isPressed -> colors.pressedTextColor
        else -> colors.defaultTextColor
    }

    Box(
        modifier = modifier
            .height(54.dp)
            .clip(shape)
            .background(backgroundColor)
            .clickable(
                enabled = enabled,
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null,
            )
            .semantics {
                role = Role.Button
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle,
            textDecoration = textDecoration,
        )
    }
}

@Immutable
data class BitnagilTextButtonColor(
    val defaultBackgroundColor: Color,
    val pressedBackgroundColor: Color,
    val disabledBackgroundColor: Color,
    val defaultTextColor: Color,
    val pressedTextColor: Color,
    val disabledTextColor: Color,
) {
    companion object {
        @Composable
        fun default(): BitnagilTextButtonColor = BitnagilTextButtonColor(
            defaultBackgroundColor = BitnagilTheme.colors.coolGray10,
            pressedBackgroundColor = BitnagilTheme.colors.coolGray5,
            disabledBackgroundColor = BitnagilTheme.colors.coolGray96,
            defaultTextColor = BitnagilTheme.colors.white,
            pressedTextColor = BitnagilTheme.colors.coolGray20,
            disabledTextColor = BitnagilTheme.colors.white,
        )

        @Composable
        fun skip(): BitnagilTextButtonColor = BitnagilTextButtonColor(
            defaultBackgroundColor = BitnagilTheme.colors.white,
            pressedBackgroundColor = BitnagilTheme.colors.white,
            disabledBackgroundColor = BitnagilTheme.colors.white,
            defaultTextColor = BitnagilTheme.colors.navy500,
            pressedTextColor = BitnagilTheme.colors.navy500,
            disabledTextColor = BitnagilTheme.colors.navy500,
        )

        @Composable
        fun cancel(): BitnagilTextButtonColor = BitnagilTextButtonColor(
            defaultBackgroundColor = BitnagilTheme.colors.coolGray97,
            pressedBackgroundColor = BitnagilTheme.colors.coolGray97,
            disabledBackgroundColor = BitnagilTheme.colors.coolGray97,
            defaultTextColor = BitnagilTheme.colors.coolGray40,
            pressedTextColor = BitnagilTheme.colors.coolGray40,
            disabledTextColor = BitnagilTheme.colors.coolGray40,
        )
    }
}

@Preview
@Composable
private fun BitnagilTextButtonPreview() {
    Column(Modifier.padding(16.dp)) {
        BitnagilTextButton(
            text = "시작하기",
            onClick = {},
            enabled = false,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(12.dp))

        BitnagilTextButton(
            text = "시작하기",
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(12.dp))

        BitnagilTextButton(
            text = "건너뛰기",
            onClick = {},
            colors = BitnagilTextButtonColor.skip(),
            textStyle = BitnagilTheme.typography.body2Regular,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
