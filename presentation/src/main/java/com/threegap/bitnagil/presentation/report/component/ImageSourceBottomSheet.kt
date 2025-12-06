package com.threegap.bitnagil.presentation.report.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageSourceBottomSheet(
    onCameraClick: () -> Unit,
    onAlbumClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    fun hideSheetAndExecute(action: () -> Unit) {
        coroutineScope
            .launch { sheetState.hide() }
            .invokeOnCompletion {
                if (!sheetState.isVisible) {
                    onDismiss()
                    action()
                }
            }
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        containerColor = BitnagilTheme.colors.white,
        contentColor = BitnagilTheme.colors.white,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        ) {
            ImageSourceOptionItem(
                iconId = R.drawable.ic_camera_gray,
                description = "카메라로 촬영하기",
                modifier = Modifier.clickableWithoutRipple(
                    onClick = { hideSheetAndExecute(onCameraClick) },
                ),
            )

            HorizontalDivider(
                color = BitnagilTheme.colors.coolGray97,
                modifier = Modifier.padding(vertical = 8.dp),
            )

            ImageSourceOptionItem(
                iconId = R.drawable.ic_outside_gray,
                description = "앨범에서 선택하기",
                modifier = Modifier.clickableWithoutRipple(
                    onClick = { hideSheetAndExecute(onAlbumClick) },
                ),
            )
        }
    }
}

@Composable
private fun ImageSourceOptionItem(
    @DrawableRes iconId: Int,
    description: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        BitnagilIcon(id = iconId, tint = null)

        Text(
            text = description,
            color = BitnagilTheme.colors.coolGray10,
            style = BitnagilTheme.typography.body1Medium,
        )
    }
}

@Preview
@Composable
private fun ImageSourceBottomSheetPreview() {
    ImageSourceBottomSheet(
        onCameraClick = {},
        onAlbumClick = {},
        onDismiss = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ImageSourceOptionItem(
        iconId = R.drawable.ic_camera_gray,
        description = "카메라로 촬영하기",
    )
}
