package com.threegap.bitnagil.presentation.intro.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviSideEffect

interface IntroSideEffect : MviSideEffect {
    data object NavigateToLogin : IntroSideEffect
}
