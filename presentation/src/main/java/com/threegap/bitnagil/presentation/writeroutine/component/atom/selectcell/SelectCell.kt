package com.threegap.bitnagil.presentation.writeroutine.component.atom.selectcell

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple

@Composable
fun SelectCell(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .background(
                color = if (selected) BitnagilTheme.colors.lightBlue100 else Color.Transparent,
                shape = RoundedCornerShape(12.dp),
            )
            .clickableWithoutRipple(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = BitnagilTheme.colors.navy400,
            style = BitnagilTheme.typography.body2Medium,
        )
    }
}
