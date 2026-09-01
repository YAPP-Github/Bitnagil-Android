package com.threegap.bitnagil.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import com.threegap.bitnagil.designsystem.color.BitnagilColors
import com.threegap.bitnagil.designsystem.color.LocalBitnagilColors
import com.threegap.bitnagil.designsystem.color.bitnagilColorsDark
import com.threegap.bitnagil.designsystem.color.bitnagilColorsLight
import com.threegap.bitnagil.designsystem.typography.BitnagilTypography
import com.threegap.bitnagil.designsystem.typography.LocalBitnagilTypography

object BitnagilTheme {
    val colors: BitnagilColors
        @Composable
        @ReadOnlyComposable
        get() = LocalBitnagilColors.current

    val typography: BitnagilTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalBitnagilTypography.current
}

@Composable
fun BitnagilTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = when {
        darkTheme -> bitnagilColorsDark()
        else -> bitnagilColorsLight()
    }
    val density = LocalDensity.current
    val typography = remember(density) { BitnagilTypography(density) }

    CompositionLocalProvider(
        LocalBitnagilTypography provides typography,
        LocalBitnagilColors provides colors,
        content = content,
    )
}
