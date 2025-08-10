package com.threegap.bitnagil.presentation.home.component.atom

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButtonColor

@Composable
fun EmotionRegisterButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    BitnagilTextButton(
        text = "오늘 감정 등록하기",
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        colors = BitnagilTextButtonColor(
            defaultBackgroundColor = BitnagilTheme.colors.orange500,
            pressedBackgroundColor = BitnagilTheme.colors.orange600,
            disabledBackgroundColor = BitnagilTheme.colors.coolGray30,
            defaultTextColor = BitnagilTheme.colors.white,
            pressedTextColor = BitnagilTheme.colors.white,
            disabledTextColor = BitnagilTheme.colors.coolGray10,
        ),
        textStyle = BitnagilTheme.typography.body2SemiBold,
        textPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        modifier = modifier
            .height(36.dp),
    )
}

@Preview
@Composable
private fun EmotionRegisterButtonPreview() {
    EmotionRegisterButton(
        onClick = {},
    )
}
