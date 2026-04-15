package com.threegap.bitnagil.designsystem.color

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class BitnagilColors(
    val white: Color = White,
    val black: Color = Black,
    val kakao: Color = Kakao,
    val error: Color = Error,
    val error10: Color = Error10,
    val skyBlue10: Color = SkyBlue10,
    val blue300: Color = Blue300,
    val purple10: Color = Purple10,
    val green10: Color = Green10,
    val green300: Color = Green300,
    val pink10: Color = Pink10,
    val yellow10: Color = Yellow10,
    val coolGray99: Color = CoolGray99,
    val coolGray98: Color = CoolGray98,
    val coolGray97: Color = CoolGray97,
    val coolGray96: Color = CoolGray96,
    val coolGray95: Color = CoolGray95,
    val coolGray90: Color = CoolGray90,
    val coolGray80: Color = CoolGray80,
    val coolGray70: Color = CoolGray70,
    val coolGray60: Color = CoolGray60,
    val coolGray50: Color = CoolGray50,
    val coolGray40: Color = CoolGray40,
    val coolGray30: Color = CoolGray30,
    val coolGray20: Color = CoolGray20,
    val coolGray10: Color = CoolGray10,
    val coolGray7: Color = CoolGray7,
    val coolGray5: Color = CoolGray5,
    val orange900: Color = Orange900,
    val orange800: Color = Orange800,
    val orange700: Color = Orange700,
    val orange600: Color = Orange600,
    val orange500: Color = Orange500,
    val orange400: Color = Orange400,
    val orange300: Color = Orange300,
    val orange200: Color = Orange200,
    val orange100: Color = Orange100,
    val orange50: Color = Orange50,
    val orange25: Color = Orange25,
)

internal fun bitnagilColorsLight(): BitnagilColors = BitnagilColors()
internal fun bitnagilColorsDark(): BitnagilColors = BitnagilColors()

internal val LocalBitnagilColors = staticCompositionLocalOf { bitnagilColorsLight() }
