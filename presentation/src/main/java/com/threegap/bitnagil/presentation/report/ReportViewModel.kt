package com.threegap.bitnagil.presentation.report

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.threegap.bitnagil.presentation.report.model.ReportCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class ReportViewModel @Inject constructor() : ViewModel(), ContainerHost<ReportState, ReportSideEffect> {

    override val container: Container<ReportState, ReportSideEffect> = container(initialState = ReportState.Init)

    fun updateReportTitle(title: String) {
        intent {
            reduce { state.copy(reportTitle = title) }
        }
    }

    fun updateReportDescription(description: String) {
        intent {
            reduce { state.copy(reportDescription = description) }
        }
    }

    fun showImageSourceBottomSheet() {
        intent {
            reduce { state.copy(imageSourceBottomSheetVisible = true) }
        }
    }

    fun hideImageSourceBottomSheet() {
        intent {
            reduce { state.copy(imageSourceBottomSheetVisible = false) }
        }
    }

    fun showReportCategoryBottomSheet() {
        intent {
            reduce { state.copy(reportCategoryBottomSheetVisible = true) }
        }
    }

    fun hideReportCategoryBottomSheet() {
        intent {
            reduce { state.copy(reportCategoryBottomSheetVisible = false) }
        }
    }

    fun selectReportCategory(category: ReportCategory) {
        intent {
            reduce { state.copy(selectedCategory = category) }
        }
    }

    fun fetchCurrentAddress() {
        // TODO
    }

    fun addImages(uris: List<Uri>) {
        intent {
            val currentImages = state.reportImages
            val availableSlots = MAX_IMAGE_COUNT - currentImages.size

            if (availableSlots > 0) {
                val imagesToAdd = uris.take(availableSlots)
                reduce { state.copy(reportImages = currentImages + imagesToAdd) }
            }
        }
    }

    fun removeImage(uri: Uri) {
        intent {
            reduce { state.copy(reportImages = state.reportImages - uri) }
        }
    }

    fun navigateToBack() {
        intent {
            postSideEffect(ReportSideEffect.NavigateToBack)
        }
    }

    companion object {
        const val MAX_IMAGE_COUNT = 3
    }
}

data class ReportState(
    val reportImages: List<Uri>,
    val reportTitle: String,
    val reportDescription: String,
    val selectedCategory: ReportCategory?,
    val imageSourceBottomSheetVisible: Boolean,
    val reportCategoryBottomSheetVisible: Boolean,
    val currentAddress: String?,
    val currentLatitude: Double?,
    val currentLongitude: Double?,
) {
    val canAddMoreImages: Boolean
        get() = reportImages.size < ReportViewModel.MAX_IMAGE_COUNT

    companion object {
        val Init = ReportState(
            reportImages = emptyList(),
            reportTitle = "",
            reportDescription = "",
            selectedCategory = null,
            imageSourceBottomSheetVisible = false,
            reportCategoryBottomSheetVisible = false,
            currentAddress = null,
            currentLatitude = null,
            currentLongitude = null,
        )
    }
}

sealed interface ReportSideEffect {
    data object NavigateToBack : ReportSideEffect
}
