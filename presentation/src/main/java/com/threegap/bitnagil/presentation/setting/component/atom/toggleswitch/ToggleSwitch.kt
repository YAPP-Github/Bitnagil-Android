package com.threegap.bitnagil.presentation.setting.component.atom.toggleswitch

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme

@Composable
fun ToggleSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val trackWidth = 44.dp
    val trackHeight = 24.dp
    val thumbDiameter = 20.dp
    val padding = (trackHeight - thumbDiameter) / 2

    val thumbOffset by animateDpAsState(
        targetValue = if (checked) trackWidth - thumbDiameter - padding else padding,
        animationSpec = tween(durationMillis = 200)
    )

    val trackColor by animateColorAsState(
        targetValue = if (checked) BitnagilTheme.colors.navy500 else BitnagilTheme.colors.coolGray95,
        animationSpec = tween(durationMillis = 200)
    )

    Box(
        modifier = Modifier
            .width(trackWidth)
            .height(trackHeight)
            .clip(CircleShape)
            .background(trackColor)
            .clickable { onCheckedChange(!checked) }
    ) {
        Box(
            modifier = Modifier
                .offset{
                    IntOffset(
                        x = thumbOffset.toPx().toInt(),
                        y = 0
                    )
                }
                .size(thumbDiameter)
                .align(Alignment.CenterStart)
                .background(BitnagilTheme.colors.white, CircleShape)
        )
    }
}
