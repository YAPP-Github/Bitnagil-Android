package com.threegap.bitnagil.designsystem.component.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme

@Composable
fun BitnagilSelectButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null,
    selected: Boolean = false,
    colors: BitnagilSelectButtonColor = BitnagilSelectButtonColor.default(),
    shape: Shape = RoundedCornerShape(12.dp),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

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

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .semantics {
                role = Role.Button
            },
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = title,
            color = contentColor,
            style = if (description == null) {
                BitnagilTheme.typography.body1Regular
            } else {
                BitnagilTheme.typography.subtitle1SemiBold
            },
        )

        description?.let {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                color = contentColor,
                style = BitnagilTheme.typography.body2Regular,
            )
        }
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
            defaultBackgroundColor = BitnagilTheme.colors.white,
            selectedBackgroundColor = BitnagilTheme.colors.lightBlue200,
            pressedBackgroundColor = BitnagilTheme.colors.lightBlue200,
            defaultContentColor = BitnagilTheme.colors.coolGray50,
            selectedContentColor = BitnagilTheme.colors.navy500,
            pressedContentColor = BitnagilTheme.colors.navy500,
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
            description = "세부 루틴 한 줄 설명",
            onClick = {},
        )
    }
}
