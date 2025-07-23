package com.threegap.bitnagil.presentation.writeroutine.component.template

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun TimePickerBottomSheetContentPreview() {
    TimePickerBottomSheetContent(
        modifier = Modifier.fillMaxWidth(),
        onTimeSelected = { _, _ -> },
        hour = 12,
        minute = 30,
    )
}
