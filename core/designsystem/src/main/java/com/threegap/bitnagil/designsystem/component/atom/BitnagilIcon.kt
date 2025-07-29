package com.threegap.bitnagil.designsystem.component.atom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple

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
        modifier = modifier,
    )
}

@Composable
fun BitnagilIconButton(
    @DrawableRes id: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    tint: Color? = BitnagilTheme.colors.black,
) {
    Box(
        modifier = modifier
            .clickableWithoutRipple(
                enabled = enabled,
                onClick = onClick,
            )
            .padding(paddingValues),
        contentAlignment = Alignment.Center,
    ) {
        BitnagilIcon(
            id = id,
            tint = tint,
        )
    }
}

@Preview
@Composable
private fun BitnagilIconPreview() {
    BitnagilIcon(
        id = R.drawable.ic_back_arrow_20,
    )
}
