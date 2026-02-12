package com.threegap.bitnagil.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.threegap.bitnagil.domain.auth.model.UserRole
import com.threegap.bitnagil.domain.auth.usecase.LoginUseCase
import com.threegap.bitnagil.presentation.login.contract.LoginSideEffect
import com.threegap.bitnagil.presentation.login.contract.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ContainerHost<LoginState, LoginSideEffect>, ViewModel() {

    override val container: Container<LoginState, LoginSideEffect> = container(initialState = LoginState.INIT)

    fun kakaoLogin(token: OAuthToken?, error: Throwable?) {
        intent {
            reduce { state.copy(isLoading = true) }
            when {
                token != null -> processKakaoLoginSuccess(token)

                error != null -> {
                    reduce { state.copy(isLoading = false) }
                    Log.e("KakaoLogin", "카카오 로그인 실패", error)
                }
            }
        }
    }

    private suspend fun processKakaoLoginSuccess(token: OAuthToken) {
        subIntent {
            loginUseCase(socialAccessToken = token.accessToken, socialType = KAKAO).fold(
                onSuccess = {
                    if (it.role == UserRole.GUEST) {
                        postSideEffect(LoginSideEffect.NavigateToTermsAgreement)
                    } else {
                        postSideEffect(LoginSideEffect.NavigateToHome)
                    }
                    reduce { state.copy(isLoading = false) }
                },
                onFailure = { e ->
                    reduce { state.copy(isLoading = false) }
                    Log.e("Login", "${e.message}")
                },
            )
        }
    }

    companion object {
        private const val KAKAO = "KAKAO"
    }
}
