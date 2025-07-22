package com.threegap.bitnagil.presentation.setting.component.atom.toggleswitch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun ManualSwitchPreview() {
    var isChecked by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.padding(16.dp).width(100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 켜진 상태
        ToggleSwitch (
            checked = isChecked,
            onCheckedChange = { checked ->
                isChecked = checked
            }
        )
    }
}
