package com.threegap.bitnagil.designsystem.typography

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.threegap.bitnagil.designsystem.font.pretendard

@Immutable
class BitnagilTypography internal constructor(
    private val _headline1: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 26,
        lineHeight = 38,
        letterSpacing = (-0.5f),
    ),
    private val _headline2: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 24,
        lineHeight = 34,
        letterSpacing = (-0.5f),
    ),
    private val _title1: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 22,
        lineHeight = 32,
        letterSpacing = (-0.5f),
    ),
    private val _title2: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 20,
        lineHeight = 30,
    ),
    private val _title3: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18,
        lineHeight = 24,
    ),
    private val _subtitle: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16,
        lineHeight = 28,
    ),
    private val _body1SemiBold: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16,
        lineHeight = 24,
    ),
    private val _body1Medium: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16,
        lineHeight = 24,
    ),
    private val _body1Regular: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16,
        lineHeight = 24,
    ),
    private val _body2SemiBold: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14,
        lineHeight = 20,
        letterSpacing = (-0.5f),
    ),
    private val _body2Medium: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14,
        lineHeight = 20,
        letterSpacing = (-0.5f),
    ),
    private val _body2Long: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14,
        lineHeight = 24,
    ),
    private val _caption1: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 12,
        lineHeight = 18,
    ),
    private val _caption1Underline: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12,
        lineHeight = 18,
        decoration = TextDecoration.Underline,
    ),
    private val _caption2Underline: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 12,
        lineHeight = 18,
        decoration = TextDecoration.Underline,
    ),
    private val _caption2: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 10,
        lineHeight = 18,
    ),
    private val _button1: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16,
        lineHeight = 24,
    ),
    private val _button2: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14,
        lineHeight = 20,
    ),
) {
    val headline1: TextStyle @Composable get() = _headline1.toDpTextStyle
    val headline2: TextStyle @Composable get() = _headline2.toDpTextStyle
    val title1: TextStyle @Composable get() = _title1.toDpTextStyle
    val title2: TextStyle @Composable get() = _title2.toDpTextStyle
    val title3: TextStyle @Composable get() = _title3.toDpTextStyle
    val subtitle: TextStyle @Composable get() = _subtitle.toDpTextStyle
    val body1SemiBold: TextStyle @Composable get() = _body1SemiBold.toDpTextStyle
    val body1Medium: TextStyle @Composable get() = _body1Medium.toDpTextStyle
    val body1Regular: TextStyle @Composable get() = _body1Regular.toDpTextStyle
    val body2SemiBold: TextStyle @Composable get() = _body2SemiBold.toDpTextStyle
    val body2Medium: TextStyle @Composable get() = _body2Medium.toDpTextStyle
    val body2Long: TextStyle @Composable get() = _body2Long.toDpTextStyle
    val caption1: TextStyle @Composable get() = _caption1.toDpTextStyle
    val caption1Underline: TextStyle @Composable get() = _caption1Underline.toDpTextStyle
    val caption2Underline: TextStyle @Composable get() = _caption2Underline.toDpTextStyle
    val caption2: TextStyle @Composable get() = _caption2.toDpTextStyle
    val button1: TextStyle @Composable get() = _button1.toDpTextStyle
    val button2: TextStyle @Composable get() = _button2.toDpTextStyle
}

internal val LocalBitnagilTypography = staticCompositionLocalOf { BitnagilTypography() }
