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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingAbstractTextItem

@Composable
fun OnBoardingAbstractTemplate(
    modifier: Modifier = Modifier,
    title: String,
    moveToNext: () -> Unit,
    onBoardingAbstractTexts: List<List<OnBoardingAbstractTextItem>>,
    onDispose: () -> Unit,
) {
    DisposableEffect(Unit) {
        moveToNext()

        onDispose {
            onDispose()
        }
    }

    val titleTextStyle = TextStyle(fontSize = 20.sp)

    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 20.dp, top = 32.dp),
    ) {
        Text(title, style = titleTextStyle)

        Spacer(modifier = Modifier.height(10.dp))

        onBoardingAbstractTexts.map { onBoardingAbstractTextItemList ->
            Spacer(modifier = Modifier.height(2.dp))
            OnBoardingAbstractText(onBoardingAbstractTextList = onBoardingAbstractTextItemList)
        }

        Spacer(modifier = Modifier.height(36.dp))

        Box(modifier = Modifier.fillMaxWidth().weight(1f).background(color = Color(0xFFC2C4C8)))
    }
}

@Composable
private fun OnBoardingAbstractText(
    onBoardingAbstractTextList: List<OnBoardingAbstractTextItem>,
) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("• ")
        }

        onBoardingAbstractTextList.map {
            withStyle(style = SpanStyle(fontWeight = if (it.isBold) FontWeight.Bold else null)) {
                append(it.text)
            }
        }
    }

    Text(
        text = annotatedString,
        style = TextStyle(fontSize = 14.sp),
    )
}
