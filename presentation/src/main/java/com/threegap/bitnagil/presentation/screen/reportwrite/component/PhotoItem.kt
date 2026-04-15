package com.threegap.bitnagil.presentation.screen.reportwrite.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIconButton

@Composable
fun PhotoItem(
    uri: Uri,
    onRemove: (Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.size(64.dp),
    ) {
        AsyncImage(
            model = uri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
        )

        BitnagilIconButton(
            id = R.drawable.ic_btn_close_sm,
            tint = null,
            onClick = { onRemove(uri) },
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.TopEnd)
                .offset(x = 10.dp, y = (-10).dp),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    PhotoItem(
        uri = "".toUri(),
        modifier = Modifier.background(color = BitnagilTheme.colors.orange50),
        onRemove = {},
    )
}
