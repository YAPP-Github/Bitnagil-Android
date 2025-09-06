package com.threegap.bitnagil.presentation.common.dimension

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp


@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }
