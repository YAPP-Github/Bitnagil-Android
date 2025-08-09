package com.threegap.bitnagil.designsystem.component.block

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIconButton
import com.threegap.bitnagil.designsystem.component.atom.BitnagilProgressBar

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
        BitnagilIconButton(
            id = R.drawable.ic_chevron_left_lg,
            onClick = onBackClick,
            modifier = Modifier.size(40.dp),
            tint = BitnagilTheme.colors.coolGray10,
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
