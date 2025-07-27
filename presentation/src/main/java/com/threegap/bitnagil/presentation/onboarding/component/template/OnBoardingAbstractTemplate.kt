package com.threegap.bitnagil.presentation.onboarding.component.template

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
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

        Spacer(modifier = Modifier.height(36.dp))

        // todo: 그래픽 작업 추가하기
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(color = Color(0xFFC2C4C8)),
        )
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
