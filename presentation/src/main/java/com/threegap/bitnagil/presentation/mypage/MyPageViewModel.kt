package com.threegap.bitnagil.presentation.mypage

import androidx.lifecycle.ViewModel
import com.threegap.bitnagil.domain.user.usecase.FetchUserProfileUseCase
import com.threegap.bitnagil.presentation.mypage.contract.MyPageSideEffect
import com.threegap.bitnagil.presentation.mypage.contract.MyPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val fetchUserProfileUseCase: FetchUserProfileUseCase,
) : ContainerHost<MyPageState, MyPageSideEffect>, ViewModel() {
    override val container: Container<MyPageState, MyPageSideEffect> = container(initialState = MyPageState.INIT)

    init {
        loadMyPageInfo()
    }

    private fun loadMyPageInfo() = intent {
        fetchUserProfileUseCase().fold(
            onSuccess = {
                reduce {
                    state.copy(
                        name = it.nickname,
                        profileUrl = "profileUrl",
                    )
                }
            },
            onFailure = {
            },
        )
    }
}
