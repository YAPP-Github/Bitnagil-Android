package com.threegap.bitnagil.presentation.setting.component.atom.settingtitle

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme

@Composable
fun SettingTitle(
    title: String,
) {
    Text(
        title,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 3.dp),
        style = BitnagilTheme.typography.caption1SemiBold.copy(color = BitnagilTheme.colors.coolGray50)
    )
}
