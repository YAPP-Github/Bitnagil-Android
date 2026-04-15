package com.threegap.bitnagil.presentation.screen.guide

import androidx.lifecycle.ViewModel
import com.threegap.bitnagil.presentation.screen.guide.contract.GuideSideEffect
import com.threegap.bitnagil.presentation.screen.guide.contract.GuideState
import com.threegap.bitnagil.presentation.screen.guide.model.GuideType
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class GuideViewModel @Inject constructor() : ContainerHost<GuideState, GuideSideEffect>, ViewModel() {

    override val container: Container<GuideState, GuideSideEffect> = container(initialState = GuideState.INIT)

    fun onShowGuideBottomSheet(guideType: GuideType) {
        intent {
            reduce {
                state.copy(guideType = guideType, guideBottomSheetVisible = true)
            }
        }
    }

    fun onHideGuideBottomSheet() {
        intent {
            reduce {
                state.copy(guideType = null, guideBottomSheetVisible = false)
            }
        }
    }

    fun navigateToBack() {
        intent {
            postSideEffect(GuideSideEffect.NavigateToBack)
        }
    }
}
