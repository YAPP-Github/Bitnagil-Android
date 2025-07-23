package com.threegap.bitnagil.presentation.setting.component.block.settingrowbutton

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme

@Composable
fun SettingRowButton(
    onClick: () -> Unit,
    text: String,
) {
    Row(
        modifier = Modifier
            .height(48.dp)
            .clickable(onClick = onClick)
            .padding(start = 16.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text,
            style = BitnagilTheme.typography.body1Regular,
            modifier = Modifier.weight(1f),
        )

        Box(
            modifier = Modifier
                .padding(end = 10.dp)
                .size(36.dp)
                .background(BitnagilTheme.colors.black),
        )
    }
}
