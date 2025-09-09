package com.threegap.bitnagil.presentation.emotion.component.atom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme

@Composable
fun SpeechBubbleText(
    text: String,
    backgroundColor: Long,
    textColor: Long,
    modifier: Modifier = Modifier,
    maxLines: Int = 2,
    minLines: Int = 2,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .background(Color(backgroundColor), shape = RoundedCornerShape(12.dp))
                .padding(20.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                style = BitnagilTheme.typography.cafe24SsurroundAir2.copy(color = Color(textColor)),
                textAlign = TextAlign.Center,
                maxLines = maxLines,
                minLines = minLines,
            )
        }

        Canvas(
            modifier = Modifier
                .height(10.dp)
                .width(24.dp),
        ) {
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width / 2, size.height)
                lineTo(0f, 0f)
                close()
            }
            drawPath(path, color = Color(backgroundColor))
        }
    }
}

@Preview
@Composable
private fun SpeechBubbleTextPreview() {
    BitnagilTheme {
        Column {
            SpeechBubbleText(
                text = "hi",
                backgroundColor = 0xFFFFFFFF,
                textColor = 0xFF000000,
                minLines = 1,
            )

            SpeechBubbleText(
                text = "안녕하세요?",
                backgroundColor = 0xFF000000,
                textColor = 0xFFFFFFFF,
                minLines = 1,
            )
        }
    }
}
