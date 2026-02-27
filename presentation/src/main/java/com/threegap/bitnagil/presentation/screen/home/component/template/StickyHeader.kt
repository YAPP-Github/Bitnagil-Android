package com.threegap.bitnagil.presentation.screen.home.component.template

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIconButton

@Composable
fun StickyHeader(
    modifier: Modifier = Modifier,
    onHelpClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BitnagilIcon(
            id = R.drawable.ic_logo,
            tint = BitnagilTheme.colors.coolGray50,
            modifier = Modifier.padding(start = 16.dp),
        )

        Spacer(modifier = Modifier.weight(1f))

        BitnagilIconButton(
            id = R.drawable.ic_help_circle,
            onClick = onHelpClick,
            paddingValues = PaddingValues(12.dp),
            tint = null,
            modifier = Modifier.padding(end = 4.dp),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    StickyHeader(onHelpClick = {})
}
