package com.threegap.bitnagil.designsystem.typography

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.font.cafe24SsurroundAir as cafe24SsurroundAirFamily
import com.threegap.bitnagil.designsystem.font.pretendard

@Immutable
class BitnagilTypography internal constructor(private val density: Density) {

    private fun textStyle(
        fontWeight: FontWeight,
        fontSize: Int,
        lineHeight: Int,
        letterSpacing: Float = 0f,
        fontFamily: FontFamily = pretendard,
        decoration: TextDecoration? = null,
    ): TextStyle = with(density) {
        TextStyle(
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            fontSize = fontSize.dp.toSp(),
            lineHeight = lineHeight.dp.toSp(),
            letterSpacing = letterSpacing.dp.toSp(),
            textDecoration = decoration,
        )
    }

    val headline1Regular = textStyle(FontWeight.Normal, fontSize = 26, lineHeight = 38, letterSpacing = -0.5f)
    val headline1Medium = headline1Regular.copy(fontWeight = FontWeight.Medium)
    val headline1Bold = headline1Regular.copy(fontWeight = FontWeight.Bold)

    val headline2Regular = textStyle(FontWeight.Normal, fontSize = 24, lineHeight = 34, letterSpacing = -0.5f)
    val headline2Medium = headline2Regular.copy(fontWeight = FontWeight.Medium)
    val headline2Bold = headline2Regular.copy(fontWeight = FontWeight.Bold)

    val title1Regular = textStyle(FontWeight.Normal, fontSize = 22, lineHeight = 32, letterSpacing = -0.5f)
    val title1Medium = title1Regular.copy(fontWeight = FontWeight.Medium)
    val title1SemiBold = title1Regular.copy(fontWeight = FontWeight.SemiBold)
    val title1Bold = title1Regular.copy(fontWeight = FontWeight.Bold)

    val title2Regular = textStyle(FontWeight.Normal, fontSize = 20, lineHeight = 30)
    val title2Medium = title2Regular.copy(fontWeight = FontWeight.Medium)
    val title2Bold = title2Regular.copy(fontWeight = FontWeight.Bold)

    val title3Regular = textStyle(FontWeight.Normal, fontSize = 18, lineHeight = 24)
    val title3Medium = title3Regular.copy(fontWeight = FontWeight.Medium)
    val title3SemiBold = title3Regular.copy(fontWeight = FontWeight.SemiBold)

    val subtitle1Regular = textStyle(FontWeight.Normal, fontSize = 16, lineHeight = 28)
    val subtitle1Medium = subtitle1Regular.copy(fontWeight = FontWeight.Medium)
    val subtitle1SemiBold = subtitle1Regular.copy(fontWeight = FontWeight.SemiBold)

    val body1Regular = textStyle(FontWeight.Normal, fontSize = 16, lineHeight = 24)
    val body1Medium = body1Regular.copy(fontWeight = FontWeight.Medium)
    val body1SemiBold = body1Regular.copy(fontWeight = FontWeight.SemiBold)

    val body2Regular = textStyle(FontWeight.Normal, fontSize = 14, lineHeight = 20, letterSpacing = -0.5f)
    val body2Medium = body2Regular.copy(fontWeight = FontWeight.Medium)
    val body2SemiBold = body2Regular.copy(fontWeight = FontWeight.SemiBold)

    val caption1Regular = textStyle(FontWeight.Normal, fontSize = 12, lineHeight = 18)
    val caption1Medium = caption1Regular.copy(fontWeight = FontWeight.Medium)
    val caption1SemiBold = caption1Regular.copy(fontWeight = FontWeight.SemiBold)

    val caption1UnderlineRegular = textStyle(FontWeight.Normal, fontSize = 12, lineHeight = 18, decoration = TextDecoration.Underline)
    val caption1UnderlineMedium = caption1UnderlineRegular.copy(fontWeight = FontWeight.Medium)
    val caption1UnderlineSemiBold = caption1UnderlineRegular.copy(fontWeight = FontWeight.SemiBold)

    val caption2Regular = textStyle(FontWeight.Normal, fontSize = 10, lineHeight = 16)
    val caption2Medium = caption2Regular.copy(fontWeight = FontWeight.Medium)
    val caption2SemiBold = caption2Regular.copy(fontWeight = FontWeight.SemiBold)

    val button1 = textStyle(FontWeight.SemiBold, fontSize = 16, lineHeight = 24)
    val button2 = textStyle(FontWeight.SemiBold, fontSize = 14, lineHeight = 20)

    val cafe24SsurroundAir = textStyle(FontWeight.Light, fontSize = 20, lineHeight = 30, letterSpacing = -0.5f, fontFamily = cafe24SsurroundAirFamily)
    val cafe24SsurroundAir2 = textStyle(FontWeight.Light, fontSize = 16, lineHeight = 24, letterSpacing = -0.5f, fontFamily = cafe24SsurroundAirFamily)
}

internal val LocalBitnagilTypography = staticCompositionLocalOf {
    BitnagilTypography(Density(density = 1f))
}
