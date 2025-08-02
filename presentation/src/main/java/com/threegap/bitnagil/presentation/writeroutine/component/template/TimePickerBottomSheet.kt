package com.threegap.bitnagil.presentation.writeroutine.component.template

import android.view.View
import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.presentation.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerBottomSheet(
    modifier: Modifier = Modifier,
    onTimeSelected: (Int, Int) -> Unit,
    hour: Int,
    minute: Int,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        containerColor = BitnagilTheme.colors.white,
    ) {
        TimePickerBottomSheetContent(
            modifier = modifier,
            onTimeSelected = { hour, minute ->
                onTimeSelected(hour, minute)
                coroutineScope
                    .launch { sheetState.hide() }
                    .invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismiss()
                        }
                    }
            },
            hour = hour,
            minute = minute,
        )
    }
}

@Composable
fun TimePickerBottomSheetContent(
    modifier: Modifier = Modifier,
    onTimeSelected: (Int, Int) -> Unit,
    hour: Int,
    minute: Int,
) {
    var currentHour by remember { mutableIntStateOf(hour) }
    var currentMinute by remember { mutableIntStateOf(minute) }

    Column(
        modifier = modifier.background(
            color = BitnagilTheme.colors.white,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AndroidView(
            modifier = Modifier.padding(vertical = 20.dp),
            factory = { context ->
                val view = View.inflate(context, R.layout.spinner_time_picker, null)
                val timePicker = view.findViewById<TimePicker>(R.id.time_picker)
                view.tag = timePicker

                TimePicker(context).apply {
                    setIs24HourView(true)
                }

                view
            },
            update = {
                val timePicker = it.tag as TimePicker

                if (timePicker.hour != hour) {
                    timePicker.hour = hour
                }
                if (timePicker.minute != minute) {
                    timePicker.minute = minute
                }

                timePicker.setOnTimeChangedListener { _, selectedHour, selectedMinute ->
                    currentHour = selectedHour
                    currentMinute = selectedMinute
                }
            },
        )

        BitnagilTextButton(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 14.dp),
            text = "저장",
            onClick = {
                onTimeSelected(currentHour, currentMinute)
            },
            enabled = true,
        )
    }
}
