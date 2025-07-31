package com.threegap.bitnagil.presentation.splash.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import kotlinx.parcelize.Parcelize

@Parcelize
data class SplashState(
    val isTokenChecked: Boolean = false,
) : MviState
