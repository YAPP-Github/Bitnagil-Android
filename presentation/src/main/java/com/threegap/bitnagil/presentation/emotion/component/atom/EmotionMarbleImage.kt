package com.threegap.bitnagil.presentation.emotion.component.atom

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.threegap.bitnagil.presentation.emotion.model.EmotionImageUiModel

@Composable
fun EmotionMarbleImage(
     modifier: Modifier,
     image: EmotionImageUiModel,
     contentDescription: String? = null,
) {
    when (image) {
        is EmotionImageUiModel.Url -> {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image.url)
                    .crossfade(true)
                    .build(),
                modifier = modifier,
                contentDescription = null,
                error = image.offlineBackupImageResourceId?.let { painterResource(it) },
            )
        }
        is EmotionImageUiModel.Resource -> {
            Image(
                painter = painterResource(id = image.resourceId),
                contentDescription = contentDescription,
                modifier = modifier,
            )
        }
    }
}
