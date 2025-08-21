package com.threegap.bitnagil.presentation.onboarding.component.template

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.presentation.common.ninepatch.ninePatchBackgroundNode
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingAbstractTextItem

@Composable
fun OnBoardingAbstractTemplate(
    modifier: Modifier = Modifier,
    title: String,
    userName: String,
    onBoardingAbstractTexts: List<List<OnBoardingAbstractTextItem>>,
    onDispose: () -> Unit,
    onClickNextButton: () -> Unit,
    nextButtonEnable: Boolean,
) {
    DisposableEffect(Unit) {
        onDispose {
            onDispose()
        }
    }

    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 20.dp, top = 53.dp),
    ) {
        Text(
            text = title,
            color = BitnagilTheme.colors.navy500,
            style = BitnagilTheme.typography.title2Bold,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(R.drawable.img_pomo_flower),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f)
                .widthIn(min = 311.dp)
                .offset(y = 2.dp),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .ninePatchBackgroundNode(R.drawable.img_texture_rectangle_4)
                .padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp),
        ) {
            Text(
                text = "${userName}님은 ...",
                style = BitnagilTheme.typography.title3SemiBold,
                color = BitnagilTheme.colors.coolGray10,
            )

            onBoardingAbstractTexts.forEachIndexed { index, onBoardingAbstractTextItemList ->
                OnBoardingAbstractText(
                    onBoardingAbstractTextList = onBoardingAbstractTextItemList,
                    iconResourceId = getIndexIconResourceId(index),
                )
            }
        }

        Spacer(modifier = Modifier.weight(2f))

        BitnagilTextButton(
            text = "다음",
            onClick = onClickNextButton,
            enabled = nextButtonEnable,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

private fun getIndexIconResourceId(index: Int): Int {
    return when (index) {
        0 -> R.drawable.img_circle_1
        1 -> R.drawable.img_circle_2
        2 -> R.drawable.img_circle_3
        else -> R.drawable.img_circle_1
    }
}

@Composable
private fun OnBoardingAbstractText(
    onBoardingAbstractTextList: List<OnBoardingAbstractTextItem>,
    iconResourceId: Int,
) {
    val annotatedString = buildAnnotatedString {
        onBoardingAbstractTextList.forEach {
            withStyle(
                style = if (it.isBold) {
                    BitnagilTheme.typography.subtitle1SemiBold.toSpanStyle().copy(color = BitnagilTheme.colors.orange500)
                } else {
                    BitnagilTheme.typography.subtitle1Medium.toSpanStyle().copy(color = BitnagilTheme.colors.coolGray10)
                },
            ) {
                append(it.text)
            }
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(iconResourceId),
            modifier = Modifier.padding(top = 8.dp).size(24.dp),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.width(14.dp))

        UnderLinedText(
            text = annotatedString,
            dividerColor = BitnagilTheme.colors.coolGray90,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun UnderLinedText(
    text: AnnotatedString,
    dividerColor: Color,
    modifier: Modifier = Modifier,
) {
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val density = LocalDensity.current
    val lineHeight = with(density) {
        val extraSpacingInDp = 39.dp
        return@with (extraSpacingInDp.value).dp.toSp()
    }

    Text(
        text = text,
        color = BitnagilTheme.colors.coolGray30,
        style = BitnagilTheme.typography.body2Regular.copy(
            lineHeight = lineHeight,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None,
            ),
        ),
        lineHeight = lineHeight,
        onTextLayout = { result ->
            textLayoutResult = result
        },
        modifier = modifier.drawBehind {
            textLayoutResult?.let {
                for (i in 0 until it.lineCount) {
                    val lineStart = 0f
                    val lineBottom = it.getLineBottom(i)
                    val lineEnd = size.width

                    val yOffset = lineBottom

                    drawLine(
                        color = dividerColor,
                        start = Offset(lineStart, yOffset),
                        end = Offset(lineEnd, yOffset),
                        strokeWidth = 2.dp.toPx(),
                    )
                }
            }
        },
    )
}

@Composable
@Preview(showBackground = true)
private fun OnBoardingAbstractTemplatePreview() {
    BitnagilTheme {
        OnBoardingAbstractTemplate(
            modifier = Modifier.fillMaxSize(),
            title = "이제 포모가 당신에게\n꼭 맞는 루틴을 찾아줄거에요.",
            userName = "안드로이드",
            onBoardingAbstractTexts = listOf(
                listOf(
                    OnBoardingAbstractTextItem(
                        text = "텍스트1",
                        isBold = true,
                    ),
                    OnBoardingAbstractTextItem(
                        text = "텍스트2 아아아아아아아아아ㅏ아앙아아ㅏ아아아아아",
                        isBold = false,
                    ),
                ),
                listOf(
                    OnBoardingAbstractTextItem(
                        text = "텍스트1",
                        isBold = true,
                    ),
                    OnBoardingAbstractTextItem(
                        text = "텍스트2",
                        isBold = false,
                    ),
                ),
                listOf(
                    OnBoardingAbstractTextItem(
                        text = "텍스트1",
                        isBold = true,
                    ),
                    OnBoardingAbstractTextItem(
                        text = "텍스트2",
                        isBold = false,
                    ),
                ),
            ),
            onDispose = {},
            onClickNextButton = {},
            nextButtonEnable = true,
        )
    }
}
