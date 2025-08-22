package com.threegap.bitnagil.presentation.common.ninepatch

import android.graphics.drawable.NinePatchDrawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.updateBounds

@Composable
fun Modifier.ninePatchBackgroundNode(drawableResId: Int): Modifier {
    val context = LocalContext.current
    val ninePatchDrawable = remember(drawableResId) {
        ContextCompat.getDrawable(context, drawableResId) as? NinePatchDrawable
    }

    return if (ninePatchDrawable != null) {
        this.then(
            Modifier.drawBehind {
                ninePatchDrawable.updateBounds(0, 0, size.width.toInt(), size.height.toInt())
                ninePatchDrawable.draw(drawContext.canvas.nativeCanvas)
            },
        )
    } else {
        this
    }
}
