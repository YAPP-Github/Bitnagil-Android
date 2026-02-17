package com.threegap.bitnagil.presentation.report

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButtonColor
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextField
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.presentation.common.extension.displayTitle
import com.threegap.bitnagil.presentation.common.file.createCameraImageUri
import com.threegap.bitnagil.presentation.common.premission.rememberPermissionHandler
import com.threegap.bitnagil.presentation.report.component.AddPhotoButton
import com.threegap.bitnagil.presentation.report.component.CurrentLocationInput
import com.threegap.bitnagil.presentation.report.component.ImageSourceBottomSheet
import com.threegap.bitnagil.presentation.report.component.PhotoItem
import com.threegap.bitnagil.presentation.report.component.ReportCategoryBottomSheet
import com.threegap.bitnagil.presentation.report.component.ReportCategorySelector
import com.threegap.bitnagil.presentation.report.component.ReportField
import com.threegap.bitnagil.presentation.report.component.template.CompleteReportContent
import com.threegap.bitnagil.presentation.report.component.template.SubmittingReportContent
import com.threegap.bitnagil.presentation.report.contract.ReportSideEffect
import com.threegap.bitnagil.presentation.report.contract.ReportState
import com.threegap.bitnagil.presentation.report.contract.ReportState.Companion.MAX_IMAGE_COUNT
import com.threegap.bitnagil.presentation.report.model.SubmitState
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ReportScreenContainer(
    navigateToBack: () -> Unit,
    viewModel: ReportViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState by viewModel.collectAsState()
    val contentFocusRequester = remember { FocusRequester() }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ReportSideEffect.NavigateToBack -> navigateToBack()
            is ReportSideEffect.FocusOnContent -> {
                delay(100)
                contentFocusRequester.requestFocus()
            }
        }
    }

    var pendingCameraPhotoUri by remember { mutableStateOf<Uri?>(null) }

    val pickMultipleMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(MAX_IMAGE_COUNT),
        onResult = viewModel::addImages,
    )

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                pendingCameraPhotoUri?.let { uri ->
                    viewModel.addImages(listOf(uri))
                }
            }
            pendingCameraPhotoUri = null
        },
    )

    val cameraPermissionHandler = rememberPermissionHandler(
        permission = Manifest.permission.CAMERA,
        dialogDescription = "카메라 권한이 비활성화됐어요.\n설정에서 허용해 주세요.",
        onGranted = {
            val imageUri = context.createCameraImageUri()
            pendingCameraPhotoUri = imageUri
            takePictureLauncher.launch(imageUri)
        },
    )

    val locationPermissionHandler = rememberPermissionHandler(
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
        dialogDescription = "위치 권한이 비활성화됐어요.\n설정에서 허용해 주세요.",
        onGranted = viewModel::fetchCurrentAddress,
    )

    cameraPermissionHandler.PermissionDialogs()
    locationPermissionHandler.PermissionDialogs()

    if (uiState.imageSourceBottomSheetVisible) {
        ImageSourceBottomSheet(
            onCameraClick = {
                if (uiState.canAddMoreImages) cameraPermissionHandler.requestPermission()
            },
            onAlbumClick = {
                if (uiState.canAddMoreImages) {
                    pickMultipleMediaLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                    )
                }
            },
            onDismiss = viewModel::hideImageSourceBottomSheet,
        )
    }

    if (uiState.reportCategoryBottomSheetVisible) {
        ReportCategoryBottomSheet(
            selectedCategory = uiState.selectedCategory,
            onSelected = viewModel::selectReportCategory,
            onDismiss = viewModel::hideReportCategoryBottomSheet,
        )
    }

    AnimatedContent(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        targetState = uiState.submitState,
        label = "ReportSlideAnimation",
        transitionSpec = {
            (
                slideIntoContainer(
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                ) togetherWith slideOutOfContainer(
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                )
                )
                .using(SizeTransform(clip = true))
        },
    ) { submitState ->
        when (submitState) {
            SubmitState.IDLE -> {
                ReportScreen(
                    uiState = uiState,
                    contentFocusRequester = contentFocusRequester,
                    onReportTitleChange = viewModel::updateReportTitle,
                    onReportContentChange = viewModel::updateReportContent,
                    onShowImageSourceBottomSheet = viewModel::showImageSourceBottomSheet,
                    onShowReportCategoryBottomSheet = viewModel::showReportCategoryBottomSheet,
                    onRemoveImage = viewModel::removeImage,
                    onGetCurrentLocationClick = locationPermissionHandler::requestPermission,
                    onSubmitClick = viewModel::submitReportWithImages,
                    onBackClick = viewModel::navigateToBack,
                )
            }
            SubmitState.SUBMITTING -> SubmittingReportContent()
            SubmitState.COMPLETE -> {
                CompleteReportContent(
                    uiState = uiState,
                    onConfirmClick = viewModel::navigateToBack,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReportScreen(
    uiState: ReportState,
    contentFocusRequester: FocusRequester,
    onReportTitleChange: (String) -> Unit,
    onReportContentChange: (String) -> Unit,
    onShowImageSourceBottomSheet: () -> Unit,
    onShowReportCategoryBottomSheet: () -> Unit,
    onRemoveImage: (Uri) -> Unit,
    onGetCurrentLocationClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.ime),
    ) {
        BitnagilTopBar(
            title = "제보하기",
            showBackButton = true,
            onBackClick = onBackClick,
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(state = scrollState)
                .padding(top = 32.dp)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp),
        ) {
            ReportField(title = "사진 첨부") {
                Row(
                    verticalAlignment = Alignment.Bottom,
                ) {
                    AddPhotoButton(
                        onClick = onShowImageSourceBottomSheet,
                        imageCount = uiState.reportImages.size,
                        maxImageCount = MAX_IMAGE_COUNT,
                    )

                    LazyRow(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp),
                    ) {
                        items(uiState.reportImages) { uri ->
                            PhotoItem(
                                uri = uri,
                                onRemove = onRemoveImage,
                            )
                        }
                    }
                }
            }

            ReportField(title = "제목") {
                BitnagilTextField(
                    value = uiState.reportTitle,
                    onValueChange = onReportTitleChange,
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            onShowReportCategoryBottomSheet()
                        },
                    ),
                    placeholder = {
                        Text(
                            text = "제보 제목을 작성해주세요.",
                            style = BitnagilTheme.typography.body2Medium,
                            color = BitnagilTheme.colors.coolGray80,
                        )
                    },
                )
            }

            ReportField(title = "카테고리") {
                ReportCategorySelector(
                    title = uiState.selectedCategory?.displayTitle,
                    onClick = {
                        focusManager.clearFocus()
                        onShowReportCategoryBottomSheet()
                    },
                )
            }

            ReportField(title = "상세 제보 내용") {
                BitnagilTextField(
                    value = uiState.reportContent,
                    onValueChange = onReportContentChange,
                    modifier = Modifier
                        .height(88.dp)
                        .focusRequester(contentFocusRequester),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        },
                    ),
                    placeholder = {
                        Text(
                            text = "어떤 위험인지 간단히 설명해주세요.(100자 내외)",
                            style = BitnagilTheme.typography.body2Medium,
                            color = BitnagilTheme.colors.coolGray80,
                        )
                    },
                )

                Text(
                    text = "${uiState.reportContent.length} / 150",
                    style = BitnagilTheme.typography.caption1Medium,
                    color = BitnagilTheme.colors.coolGray80,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            ReportField(title = "신고 위치") {
                CurrentLocationInput(
                    currentLocation = uiState.currentAddress,
                    onClick = onGetCurrentLocationClick,
                )
            }
        }

        BitnagilTextButton(
            text = "제보하기",
            onClick = onSubmitClick,
            colors = BitnagilTextButtonColor.default(
                disabledBackgroundColor = BitnagilTheme.colors.coolGray98,
                disabledTextColor = BitnagilTheme.colors.coolGray90,
            ),
            enabled = uiState.isSubmittable,
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ReportScreen(
        uiState = ReportState.Init,
        contentFocusRequester = remember { FocusRequester() },
        onReportTitleChange = {},
        onReportContentChange = {},
        onRemoveImage = {},
        onShowImageSourceBottomSheet = {},
        onShowReportCategoryBottomSheet = {},
        onGetCurrentLocationClick = {},
        onSubmitClick = {},
        onBackClick = {},
    )
}
