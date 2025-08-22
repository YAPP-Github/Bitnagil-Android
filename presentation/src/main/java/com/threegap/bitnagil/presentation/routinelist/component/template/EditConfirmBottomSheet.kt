package com.threegap.bitnagil.presentation.routinelist.component.template

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditConfirmBottomSheet(
    onDismissRequest: () -> Unit,
    onApplyToday: () -> Unit,
    onApplyTomorrow: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        contentColor = BitnagilTheme.colors.white,
        containerColor = BitnagilTheme.colors.white,
        modifier = modifier,
    ) {
        EditConfirmContent(
            onApplyToday = {
                onApplyToday()
                coroutineScope
                    .launch { sheetState.hide() }
                    .invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismissRequest()
                        }
                    }
            },
            onApplyTomorrow = {
                onApplyTomorrow()
                coroutineScope
                    .launch { sheetState.hide() }
                    .invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismissRequest()
                        }
                    }
            },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 26.dp),
        )
    }
}

@Composable
private fun EditConfirmContent(
    onApplyToday: () -> Unit,
    onApplyTomorrow: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "변경한 루틴, 언제 시작할까요?",
            color = BitnagilTheme.colors.coolGray10,
            style = BitnagilTheme.typography.title3SemiBold,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "변경된 루틴이 반영되는 시점을 선택해 주세요.",
            color = BitnagilTheme.colors.coolGray40,
            style = BitnagilTheme.typography.body2Medium,
        )

        Spacer(modifier = Modifier.height(24.dp))

        BitnagilTextButton(
            text = "당일부터 적용",
            onClick = onApplyToday,
            modifier = Modifier.fillMaxWidth(),
            textStyle = BitnagilTheme.typography.body2Medium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        BitnagilTextButton(
            text = "다음 날부터 적용",
            onClick = onApplyTomorrow,
            modifier = Modifier.fillMaxWidth(),
            textStyle = BitnagilTheme.typography.body2Medium,
        )
    }
}

@Preview
@Composable
private fun EditConfirmContentPreview() {
    EditConfirmContent(
        onApplyToday = {},
        onApplyTomorrow = {},
    )
}
