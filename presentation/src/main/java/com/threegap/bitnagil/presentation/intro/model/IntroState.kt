package com.threegap.bitnagil.presentation.intro.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import kotlinx.parcelize.Parcelize

@Parcelize
data class IntroState(
    val isLoading: Boolean = false,
) : MviState
