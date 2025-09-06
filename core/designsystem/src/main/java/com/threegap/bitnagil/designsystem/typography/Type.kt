package com.threegap.bitnagil.designsystem.typography

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.threegap.bitnagil.designsystem.font.cafe24SsurroundAir
import com.threegap.bitnagil.designsystem.font.pretendard

@Immutable
class BitnagilTypography internal constructor(
    private val _headline1: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 26,
        lineHeight = 38,
        letterSpacing = (-0.5f),
    ),
    private val _headline2: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 24,
        lineHeight = 34,
        letterSpacing = (-0.5f),
    ),
    private val _title1: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 22,
        lineHeight = 32,
        letterSpacing = (-0.5f),
    ),
    private val _title2: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 20,
        lineHeight = 30,
    ),
    private val _title3: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 18,
        lineHeight = 24,
    ),
    private val _subtitle1: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16,
        lineHeight = 28,
    ),
    private val _body1: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16,
        lineHeight = 24,
    ),
    private val _body2: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 14,
        lineHeight = 20,
        letterSpacing = (-0.5f),
    ),
    private val _caption1: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 12,
        lineHeight = 18,
    ),
    private val _caption1Underline: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 12,
        lineHeight = 18,
        decoration = TextDecoration.Underline,
    ),
    private val _caption2: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 10,
        lineHeight = 16,
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
    private val _cafe24SsurroundAir: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = cafe24SsurroundAir,
        fontWeight = FontWeight.Light,
        fontSize = 20,
        lineHeight = 30,
        letterSpacing = (-0.5f),
    ),
    private val _cafe24SsurroundAir2: BitnagilTextStyle = BitnagilTextStyle(
        fontFamily = cafe24SsurroundAir,
        fontWeight = FontWeight.Light,
        fontSize = 16,
        lineHeight = 24,
        letterSpacing = (-0.5f),
    ),
) {
    private val _headline1Bold = _headline1.copy(fontWeight = FontWeight.Bold)
    private val _headline1Medium = _headline1.copy(fontWeight = FontWeight.Medium)
    private val _headline2Bold = _headline2.copy(fontWeight = FontWeight.Bold)
    private val _headline2Medium = _headline2.copy(fontWeight = FontWeight.Medium)
    private val _title1Bold = _title1.copy(fontWeight = FontWeight.Bold)
    private val _title1SemiBold = _title1.copy(fontWeight = FontWeight.SemiBold)
    private val _title1Medium = _title1.copy(fontWeight = FontWeight.Medium)
    private val _title2Bold = _title2.copy(fontWeight = FontWeight.Bold)
    private val _title2Medium = _title2.copy(fontWeight = FontWeight.Medium)
    private val _title3SemiBold = _title3.copy(fontWeight = FontWeight.SemiBold)
    private val _title3Medium = _title3.copy(fontWeight = FontWeight.Medium)
    private val _subtitle1SemiBold = _subtitle1.copy(fontWeight = FontWeight.SemiBold)
    private val _subtitle1Medium = _subtitle1.copy(fontWeight = FontWeight.Medium)
    private val _body1SemiBold = _body1.copy(fontWeight = FontWeight.SemiBold)
    private val _body1Medium = _body1.copy(fontWeight = FontWeight.Medium)
    private val _body2SemiBold = _body2.copy(fontWeight = FontWeight.SemiBold)
    private val _body2Medium = _body2.copy(fontWeight = FontWeight.Medium)
    private val _caption1SemiBold = _caption1.copy(fontWeight = FontWeight.SemiBold)
    private val _caption1Medium = _caption1.copy(fontWeight = FontWeight.Medium)
    private val _caption1UnderlineSemiBold = _caption1Underline.copy(fontWeight = FontWeight.SemiBold)
    private val _caption1UnderlineMedium = _caption1Underline.copy(fontWeight = FontWeight.Medium)
    private val _caption2SemiBold = _caption2.copy(fontWeight = FontWeight.SemiBold)
    private val _caption2Medium = _caption2.copy(fontWeight = FontWeight.Medium)

    val headline1Bold: TextStyle @Composable get() = _headline1Bold.toDpTextStyle
    val headline1Medium: TextStyle @Composable get() = _headline1Medium.toDpTextStyle
    val headline1Regular: TextStyle @Composable get() = _headline1.toDpTextStyle
    val headline2Bold: TextStyle @Composable get() = _headline2Bold.toDpTextStyle
    val headline2Medium: TextStyle @Composable get() = _headline2Medium.toDpTextStyle
    val headline2Regular: TextStyle @Composable get() = _headline2.toDpTextStyle
    val title1Bold: TextStyle @Composable get() = _title1Bold.toDpTextStyle
    val title1SemiBold: TextStyle @Composable get() = _title1SemiBold.toDpTextStyle
    val title1Medium: TextStyle @Composable get() = _title1Medium.toDpTextStyle
    val title1Regular: TextStyle @Composable get() = _title1.toDpTextStyle
    val title2Bold: TextStyle @Composable get() = _title2Bold.toDpTextStyle
    val title2Medium: TextStyle @Composable get() = _title2Medium.toDpTextStyle
    val title2Regular: TextStyle @Composable get() = _title2.toDpTextStyle
    val title3SemiBold: TextStyle @Composable get() = _title3SemiBold.toDpTextStyle
    val title3Medium: TextStyle @Composable get() = _title3Medium.toDpTextStyle
    val title3Regular: TextStyle @Composable get() = _title3.toDpTextStyle
    val subtitle1SemiBold: TextStyle @Composable get() = _subtitle1SemiBold.toDpTextStyle
    val subtitle1Medium: TextStyle @Composable get() = _subtitle1Medium.toDpTextStyle
    val subtitle1Regular: TextStyle @Composable get() = _subtitle1.toDpTextStyle
    val body1SemiBold: TextStyle @Composable get() = _body1SemiBold.toDpTextStyle
    val body1Medium: TextStyle @Composable get() = _body1Medium.toDpTextStyle
    val body1Regular: TextStyle @Composable get() = _body1.toDpTextStyle
    val body2SemiBold: TextStyle @Composable get() = _body2SemiBold.toDpTextStyle
    val body2Medium: TextStyle @Composable get() = _body2Medium.toDpTextStyle
    val body2Regular: TextStyle @Composable get() = _body2.toDpTextStyle
    val caption1SemiBold: TextStyle @Composable get() = _caption1SemiBold.toDpTextStyle
    val caption1Medium: TextStyle @Composable get() = _caption1Medium.toDpTextStyle
    val caption1Regular: TextStyle @Composable get() = _caption1.toDpTextStyle
    val caption1UnderlineSemiBold: TextStyle @Composable get() = _caption1UnderlineSemiBold.toDpTextStyle
    val caption1UnderlineMedium: TextStyle @Composable get() = _caption1UnderlineMedium.toDpTextStyle
    val caption1UnderlineRegular: TextStyle @Composable get() = _caption1Underline.toDpTextStyle
    val caption2SemiBold: TextStyle @Composable get() = _caption2SemiBold.toDpTextStyle
    val caption2Medium: TextStyle @Composable get() = _caption2Medium.toDpTextStyle
    val caption2Regular: TextStyle @Composable get() = _caption2.toDpTextStyle
    val button1: TextStyle @Composable get() = _button1.toDpTextStyle
    val button2: TextStyle @Composable get() = _button2.toDpTextStyle
    val cafe24SsurroundAir: TextStyle @Composable get() = _cafe24SsurroundAir.toDpTextStyle
    val cafe24SsurroundAir2: TextStyle @Composable get() = _cafe24SsurroundAir2.toDpTextStyle
}

internal val LocalBitnagilTypography = staticCompositionLocalOf { BitnagilTypography() }
