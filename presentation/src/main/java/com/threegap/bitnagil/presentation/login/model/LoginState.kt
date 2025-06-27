package com.threegap.bitnagil.presentation.login.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginState(
    val isLoggedIn: Boolean = false,
) : MviState
