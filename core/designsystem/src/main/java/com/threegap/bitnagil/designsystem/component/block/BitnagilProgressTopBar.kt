package com.threegap.bitnagil.designsystem.component.block

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.component.atom.BitnagilProgressBar
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple

@Composable
fun BitnagilProgressTopBar(
    progress: Float,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .padding(start = 4.dp, end = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        BitnagilIcon(
            id = R.drawable.ic_back_arrow_36,
            modifier = Modifier.clickableWithoutRipple(onClick = onBackClick),
        )

        BitnagilProgressBar(
            progress = progress,
            modifier = Modifier,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BitnagilProgressTopBarPreview() {
    BitnagilProgressTopBar(
        progress = 0.5f,
        onBackClick = {},
    )
}
