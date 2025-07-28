package com.threegap.bitnagil.designsystem.component.atom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R

@Composable
fun BitnagilIcon(
    @DrawableRes id: Int,
    modifier: Modifier = Modifier,
    tint: Color? = BitnagilTheme.colors.black,
) {
    Image(
        imageVector = ImageVector.vectorResource(id),
        contentDescription = null,
        colorFilter = tint?.let { ColorFilter.tint(it) },
        modifier = modifier
    )
}

@Preview
@Composable
private fun BitnagilIconPreview() {
    BitnagilIcon(
        id = R.drawable.ic_back_arrow_20,
    )
}
