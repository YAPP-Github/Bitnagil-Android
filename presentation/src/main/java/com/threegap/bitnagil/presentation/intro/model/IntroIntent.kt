package com.threegap.bitnagil.presentation.intro.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent

sealed class IntroIntent : MviIntent {
    data object OnStartButtonClick : IntroIntent()
}
