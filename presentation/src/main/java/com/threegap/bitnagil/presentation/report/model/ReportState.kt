package com.threegap.bitnagil.presentation.report.model

import android.net.Uri
import com.threegap.bitnagil.domain.report.model.ReportCategory

data class ReportState(
    val reportImages: List<Uri>,
    val reportTitle: String,
    val reportContent: String,
    val selectedCategory: ReportCategory?,
    val imageSourceBottomSheetVisible: Boolean,
    val reportCategoryBottomSheetVisible: Boolean,
    val currentAddress: String?,
    val currentLatitude: Double?,
    val currentLongitude: Double?,
    val uploadedImagePaths: List<String>,
    val submitState: SubmitState,
) {
    val canAddMoreImages: Boolean
        get() = reportImages.size < MAX_IMAGE_COUNT

    val isSubmittable: Boolean
        get() = reportImages.isNotEmpty() &&
            reportTitle.isNotEmpty() &&
            reportContent.isNotEmpty() &&
            selectedCategory != null &&
            currentAddress != null &&
            currentLatitude != null &&
            currentLongitude != null

    companion object {
        const val MAX_IMAGE_COUNT = 3

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
