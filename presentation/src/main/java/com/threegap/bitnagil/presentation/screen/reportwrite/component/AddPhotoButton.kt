package com.threegap.bitnagil.presentation.screen.reportwrite.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple

@Composable
fun AddPhotoButton(
    onClick: () -> Unit,
    imageCount: Int,
    maxImageCount: Int,
    modifier: Modifier = Modifier,
) {
    val enabled = imageCount < maxImageCount
    val imageCountColor =
        if (imageCount == 0) BitnagilTheme.colors.coolGray70 else BitnagilTheme.colors.black

    Column(
        modifier = modifier
            .background(
                color = BitnagilTheme.colors.coolGray99,
                shape = RoundedCornerShape(8.dp),
            )
            .size(64.dp)
            .clickableWithoutRipple(
                onClick = onClick,
                enabled = enabled,
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BitnagilIcon(
            id = R.drawable.ic_camera,
            tint = null,
        )
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = imageCountColor)) {
                    append("$imageCount")
                }
                append("/$maxImageCount")
            },
            color = BitnagilTheme.colors.coolGray70,
            style = BitnagilTheme.typography.caption1Medium,
        )
    }
}

@Preview
@Composable
private fun CameraButtonPreview() {
    AddPhotoButton(
        onClick = {},
        imageCount = 0,
        maxImageCount = 3,
    )
}
