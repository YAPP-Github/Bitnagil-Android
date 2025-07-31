package com.threegap.bitnagil.presentation.splash.component.template

import androidx.annotation.RawRes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.threegap.bitnagil.designsystem.R

@Composable
fun BitnagilLottieAnimation(
    @RawRes lottieJson: Int,
    maxFrame: Int,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
    onStart: (() -> Unit)? = null,
) {
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieJson))
    val lottieAnimatable = rememberLottieAnimatable()

    val alpha by animateFloatAsState(
        targetValue = if (lottieComposition != null) 1f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing,
        ),
        label = "lottieAlpha",
    )

    LaunchedEffect(lottieComposition) {
        lottieComposition?.let {
            onStart?.invoke()
            lottieAnimatable.animate(
                composition = it,
                clipSpec = LottieClipSpec.Frame(0, maxFrame),
                iterations = 1,
                ignoreSystemAnimationsDisabled = true,
                useCompositionFrameRate = true,
            )
            onComplete()
        }
    }

    LottieAnimation(
        composition = lottieComposition,
        progress = { lottieAnimatable.progress },
        modifier = modifier.alpha(alpha),
        clipToCompositionBounds = false,
    )
}

@Preview(showBackground = true)
@Composable
private fun BitnagilLottieAnimationPreview() {
    BitnagilLottieAnimation(
        lottieJson = R.raw.splash_lottie,
        maxFrame = 800,
        onComplete = {},
        modifier = Modifier.fillMaxSize(),
    )
}
