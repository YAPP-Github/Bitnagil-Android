package com.threegap.bitnagil.presentation.screen.mypage

import androidx.lifecycle.ViewModel
import com.threegap.bitnagil.domain.user.usecase.ObserveUserProfileUseCase
import com.threegap.bitnagil.presentation.screen.mypage.contract.MyPageSideEffect
import com.threegap.bitnagil.presentation.screen.mypage.contract.MyPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val observeUserProfileUseCase: ObserveUserProfileUseCase,
) : ContainerHost<MyPageState, MyPageSideEffect>, ViewModel() {

    override val container: Container<MyPageState, MyPageSideEffect> =
        container(
            initialState = MyPageState.INIT,
            buildSettings = { repeatOnSubscribedStopTimeout = 5_000L },
            onCreate = {
                repeatOnSubscription {
                    observeUserProfileUseCase().collect { result ->
                        result.fold(
                            onSuccess = {
                                reduce { state.copy(name = it.nickname, profileUrl = "profileUrl") }
                            },
                            onFailure = {},
                        )
                    }
                }
            },
        )
}
