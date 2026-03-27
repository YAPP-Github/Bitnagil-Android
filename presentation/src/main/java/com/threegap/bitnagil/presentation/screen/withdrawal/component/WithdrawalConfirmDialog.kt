package com.threegap.bitnagil.presentation.screen.withdrawal.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.threegap.bitnagil.designsystem.component.block.BitnagilConfirmDialog

@Composable
fun WithdrawalConfirmDialog(
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BitnagilConfirmDialog(
        title = "탈퇴가 완료되었어요",
        description = "이용해 주셔서 감사합니다. 언제든 다시\n돌아오실 수 있어요:)",
        confirmButtonText = "확인",
        onConfirm = onConfirm,
        modifier = modifier,
        dismissOnBackPress = false,
        dismissOnClickOutside = false,
    )
}

@Preview
@Composable
private fun WithdrawalConfirmDialogPreview() {
    WithdrawalConfirmDialog(
        onConfirm = {},
    )
}
