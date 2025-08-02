package com.threegap.bitnagil.presentation.onboarding.component.template

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingAbstractTextItem

@Composable
fun OnBoardingAbstractTemplate(
    modifier: Modifier = Modifier,
    title: String,
    onBoardingAbstractTexts: List<List<OnBoardingAbstractTextItem>>,
    onInit: () -> Unit,
    onDispose: () -> Unit,
) {
    DisposableEffect(Unit) {
        onInit()

        onDispose {
            onDispose()
        }
    }

    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 20.dp, top = 32.dp),
    ) {
        Text(
            text = title,
            color = BitnagilTheme.colors.navy500,
            style = BitnagilTheme.typography.title2Bold,
        )

        Spacer(modifier = Modifier.height(10.dp))

        onBoardingAbstractTexts.forEach { onBoardingAbstractTextItemList ->
            Spacer(modifier = Modifier.height(2.dp))
            OnBoardingAbstractText(onBoardingAbstractTextList = onBoardingAbstractTextItemList)
        }

        Spacer(modifier = Modifier.height(83.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.TopCenter,
        ) {
            Image(
                painter = painterResource(R.drawable.onboarding_character),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .aspectRatio(295f / 280f),
            )
        }
    }
}

@Composable
private fun OnBoardingAbstractText(
    onBoardingAbstractTextList: List<OnBoardingAbstractTextItem>,
) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = BitnagilTheme.typography.body2SemiBold.toSpanStyle()) {
            append("• ")
        }

        onBoardingAbstractTextList.forEach {
            withStyle(
                style = if (it.isBold) {
                    BitnagilTheme.typography.body2SemiBold.toSpanStyle()
                } else {
                    BitnagilTheme.typography.body2Regular.toSpanStyle()
                },
            ) {
                append(it.text)
            }
        }
    }

    Text(
        text = annotatedString,
        color = BitnagilTheme.colors.coolGray30,
        style = BitnagilTheme.typography.body2Regular,
    )
}
