package com.threegap.bitnagil.presentation.report

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.threegap.bitnagil.domain.address.usecase.FetchCurrentAddressUseCase
import com.threegap.bitnagil.domain.file.usecase.UploadReportImagesUseCase
import com.threegap.bitnagil.domain.report.model.Report
import com.threegap.bitnagil.domain.report.usecase.SubmitReportUseCase
import com.threegap.bitnagil.presentation.common.file.convertUriToImageFile
import com.threegap.bitnagil.presentation.report.model.ReportCategoryUi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class ReportViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fetchCurrentAddressUseCase: FetchCurrentAddressUseCase,
    private val uploadReportImagesUseCase: UploadReportImagesUseCase,
    private val submitReportUseCase: SubmitReportUseCase,
) : ViewModel(), ContainerHost<ReportState, ReportSideEffect> {

    override val container: Container<ReportState, ReportSideEffect> = container(initialState = ReportState.Init)

    fun updateReportTitle(title: String) {
        intent {
            reduce { state.copy(reportTitle = title) }
        }
    }

    fun updateReportContent(content: String) {
        intent {
            reduce { state.copy(reportContent = content) }
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

    fun selectReportCategory(category: ReportCategoryUi) {
        intent {
            reduce { state.copy(selectedCategory = category) }
        }
    }

    fun fetchCurrentAddress() {
        intent {
            fetchCurrentAddressUseCase().fold(
                onSuccess = {
                    reduce {
                        state.copy(
                            currentLatitude = it.latitude,
                            currentLongitude = it.longitude,
                            currentAddress = it.roadAddress,
                        )
                    }
                },
                onFailure = {},
            )
        }
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

    fun submitReportWithImages() {
        intent {
            val category = state.selectedCategory ?: return@intent
            val address = state.currentAddress ?: return@intent
            val latitude = state.currentLatitude ?: return@intent
            val longitude = state.currentLongitude ?: return@intent

            reduce { state.copy(submitState = SubmitState.SUBMITTING) }

            val imageFiles = coroutineScope {
                state.reportImages
                    .map { uri ->
                        async {
                            convertUriToImageFile(uri = uri, prefix = "report", context = context)
                        }
                    }
                    .awaitAll()
                    .filterNotNull()
            }

            if (imageFiles.size < state.reportImages.size) {
                reduce { state.copy(submitState = SubmitState.ERROR) }
                return@intent
            }

            val uploadedPaths = uploadReportImagesUseCase(imageFiles).getOrElse { error ->
                reduce { state.copy(submitState = SubmitState.ERROR) }
                return@intent
            }

            reduce { state.copy(uploadedImagePaths = uploadedPaths) }

            submitReportUseCase(
                Report(
                    title = state.reportTitle,
                    content = state.reportContent,
                    category = category.toDomain(),
                    address = address,
                    latitude = latitude,
                    longitude = longitude,
                    imageUrls = uploadedPaths,
                ),
            ).fold(
                onSuccess = {
                    reduce { state.copy(submitState = SubmitState.SUCCESS) }
                },
                onFailure = { it ->
                    reduce { state.copy(submitState = SubmitState.ERROR) }
                },
            )
        }
    }

    companion object {
        const val MAX_IMAGE_COUNT = 3
    }
}

data class ReportState(
    val reportImages: List<Uri>,
    val reportTitle: String,
    val reportContent: String,
    val selectedCategory: ReportCategoryUi?,
    val imageSourceBottomSheetVisible: Boolean,
    val reportCategoryBottomSheetVisible: Boolean,
    val currentAddress: String?,
    val currentLatitude: Double?,
    val currentLongitude: Double?,
    val uploadedImagePaths: List<String>,
    val submitState: SubmitState,
) {
    val canAddMoreImages: Boolean
        get() = reportImages.size < ReportViewModel.MAX_IMAGE_COUNT

    val isSubmittable: Boolean
        get() = reportImages.isNotEmpty() &&
            reportTitle.isNotEmpty() &&
            reportContent.isNotEmpty() &&
            selectedCategory != null &&
            currentAddress != null &&
            currentLatitude != null &&
            currentLongitude != null

    companion object {
        val Init = ReportState(
            reportImages = emptyList(),
            reportTitle = "",
            reportContent = "",
            selectedCategory = null,
            imageSourceBottomSheetVisible = false,
            reportCategoryBottomSheetVisible = false,
            currentAddress = null,
            currentLatitude = null,
            currentLongitude = null,
            uploadedImagePaths = emptyList(),
            submitState = SubmitState.IDLE,
        )
    }
}

enum class SubmitState {
    IDLE,
    SUBMITTING,
    SUCCESS,
    ERROR,
}

sealed interface ReportSideEffect {
    data object NavigateToBack : ReportSideEffect
}
