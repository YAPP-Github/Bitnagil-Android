package com.threegap.bitnagil.presentation.report

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.threegap.bitnagil.domain.address.usecase.FetchCurrentAddressUseCase
import com.threegap.bitnagil.domain.file.usecase.UploadReportImagesUseCase
import com.threegap.bitnagil.domain.report.model.Report
import com.threegap.bitnagil.domain.report.model.ReportCategory
import com.threegap.bitnagil.domain.report.usecase.SubmitReportUseCase
import com.threegap.bitnagil.presentation.common.file.convertUriToImageFile
import com.threegap.bitnagil.presentation.report.contract.ReportSideEffect
import com.threegap.bitnagil.presentation.report.contract.ReportState
import com.threegap.bitnagil.presentation.report.contract.ReportState.Companion.MAX_IMAGE_COUNT
import com.threegap.bitnagil.presentation.report.model.SubmitState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

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
            if (title.length > ReportState.MAX_TITLE_LENGTH) return@intent;
            reduce { state.copy(reportTitle = title) }
        }
    }

    fun updateReportContent(content: String) {
        intent {
            if (content.length > ReportState.MAX_CONTENT_LENGTH) return@intent;
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
            postSideEffect(ReportSideEffect.FocusOnContent)
        }
    }

    fun selectReportCategory(category: ReportCategory) {
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

            coroutineScope {
                val minDelayJob = async {
                    delay(timeMillis = ReportState.MIN_LOADING_TIME)
                }

                val processingJob = async {
                    val imageFiles = state.reportImages
                        .map { uri ->
                            async { convertUriToImageFile(uri, "report", context) }
                        }
                        .awaitAll()
                        .filterNotNull()

                    if (imageFiles.size < state.reportImages.size) {
                        return@async Result.failure(Exception("Image conversion failed"))
                    }

                    val uploadedPaths = uploadReportImagesUseCase(imageFiles)
                        .getOrElse { return@async Result.failure(it) }

                    val submitResult = submitReportUseCase(
                        Report(
                            title = state.reportTitle,
                            content = state.reportContent,
                            category = category,
                            address = address,
                            latitude = latitude,
                            longitude = longitude,
                            imageUrls = uploadedPaths,
                        ),
                    )

                    submitResult.map { uploadedPaths }
                }

                minDelayJob.await()
                val result = processingJob.await()

                result.fold(
                    onSuccess = { paths ->
                        reduce {
                            state.copy(
                                submitState = SubmitState.COMPLETE,
                                uploadedImagePaths = paths,
                            )
                        }
                    },
                    onFailure = { _ ->
                        reduce { state.copy(submitState = SubmitState.IDLE) }
                    },
                )
            }
        }
    }
}
