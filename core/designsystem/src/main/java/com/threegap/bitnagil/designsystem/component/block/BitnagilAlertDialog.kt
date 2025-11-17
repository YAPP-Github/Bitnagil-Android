package com.threegap.bitnagil.designsystem.component.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButtonColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BitnagilAlertDialog(
    title: String,
    description: String,
    dismissButtonText: String,
    confirmButtonText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
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
                    shape = RoundedCornerShape(12.dp),
                )
                .padding(vertical = 20.dp, horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = title,
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.subtitle1SemiBold,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = description,
                color = BitnagilTheme.colors.coolGray40,
                style = BitnagilTheme.typography.body2Medium,
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.height(48.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                BitnagilTextButton(
                    text = dismissButtonText,
                    onClick = onDismiss,
                    colors = BitnagilTextButtonColor.cancel(),
                    textStyle = BitnagilTheme.typography.body2Medium,
                    modifier = Modifier.weight(1f),
                )

                BitnagilTextButton(
                    text = confirmButtonText,
                    onClick = onConfirm,
                    textStyle = BitnagilTheme.typography.body2Medium,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    BitnagilAlertDialog(
        title = "로그아웃 할까요?",
        description = "이 기기에서 계정이 로그아웃되고, 다시\n로그인해야 서비스를 계속 이용할 수 있어요.",
        dismissButtonText = "취소",
        confirmButtonText = "로그아웃",
        onDismiss = {},
        onConfirm = {},
    )
}
