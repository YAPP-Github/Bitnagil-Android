package com.threegap.bitnagil.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.threegap.bitnagil.domain.auth.model.UserRole
import com.threegap.bitnagil.domain.auth.usecase.AutoLoginUseCase
import com.threegap.bitnagil.domain.version.usecase.CheckUpdateRequirementUseCase
import com.threegap.bitnagil.presentation.screen.splash.contract.SplashSideEffect
import com.threegap.bitnagil.presentation.screen.splash.contract.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkUpdateRequirementUseCase: CheckUpdateRequirementUseCase,
    private val autoLoginUseCase: AutoLoginUseCase,
) : ContainerHost<SplashState, SplashSideEffect>, ViewModel() {

    override val container: Container<SplashState, SplashSideEffect> = container(initialState = SplashState.INIT)

    init {
        performForceUpdateCheck()
    }

    private fun performForceUpdateCheck() {
        intent {
            val isUpdateRequired = withTimeoutOrNull(5000) {
                checkUpdateRequirementUseCase().getOrElse { false }
            } ?: false
            reduce { state.copy(forceUpdateRequired = isUpdateRequired, isForceUpdateCheckCompleted = true) }

            if (!isUpdateRequired) { performAutoLogin() }
        }
    }

    private fun performAutoLogin() {
        intent {
            try {
                val userRole = withTimeoutOrNull(5000) {
                    autoLoginUseCase()
                }
                reduce { state.copy(userRole = userRole, isAutoLoginCompleted = true) }
            } catch (e: Exception) {
                reduce { state.copy(userRole = null, isAutoLoginCompleted = true) }
            }
        }
    }

    fun onAnimationCompleted() {
        intent {
            if (state.forceUpdateRequired) return@intent
            if (!state.isAutoLoginCompleted) {
                viewModelScope.launch {
                    delay(100)
                    onAnimationCompleted()
                }
                return@intent
            }

            when (state.userRole) {
                UserRole.GUEST -> postSideEffect(SplashSideEffect.NavigateToTermsAgreement)
                UserRole.USER -> postSideEffect(SplashSideEffect.NavigateToHome)
                UserRole.ONBOARDING -> postSideEffect(SplashSideEffect.NavigateToOnboarding)
                else -> postSideEffect(SplashSideEffect.NavigateToLogin)
            }
        }
    }
}
