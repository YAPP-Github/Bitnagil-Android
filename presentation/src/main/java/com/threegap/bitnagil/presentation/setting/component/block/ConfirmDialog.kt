package com.threegap.bitnagil.presentation.setting.component.block

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButtonColor
import com.threegap.bitnagil.presentation.setting.model.ConfirmDialogType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDialog(
    type: ConfirmDialogType,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = BitnagilTheme.colors.white,
                    shape = RoundedCornerShape(20.dp),
                )
                .padding(vertical = 24.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BitnagilIcon(
                id = R.drawable.ic_modal_warning,
                tint = null,
                modifier = Modifier.size(55.dp),
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = type.titleText,
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.title2Bold,
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = type.descriptionText,
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.caption1Regular,
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.height(44.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                BitnagilTextButton(
                    text = "취소",
                    onClick = onDismiss,
                    colors = BitnagilTextButtonColor.skip(),
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = BitnagilTheme.colors.navy500,
                            shape = RoundedCornerShape(8.dp),
                        ),
                )

                BitnagilTextButton(
                    text = type.confirmButtonText,
                    onClick = onConfirm,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Preview
@Composable
private fun ConfirmDialogPreview() {
    ConfirmDialog(
        type = ConfirmDialogType.LOGOUT,
        onDismiss = {},
        onConfirm = {},
    )
}
