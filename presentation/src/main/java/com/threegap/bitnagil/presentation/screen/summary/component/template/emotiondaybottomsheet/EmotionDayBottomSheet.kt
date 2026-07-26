package com.threegap.bitnagil.presentation.screen.summary.component.template.emotiondaybottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIconButton
import com.threegap.bitnagil.presentation.screen.summary.model.SummaryEmotionDayUiModel
import com.threegap.bitnagil.presentation.screen.summary.model.SummaryEmotionType
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmotionDayBottomSheet(
    onDismiss: () -> Unit,
    emotionDay: SummaryEmotionDayUiModel,
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        containerColor = BitnagilTheme.colors.coolGray99,
        contentColor = BitnagilTheme.colors.coolGray99,
        dragHandle = null,
    ) {
        EmotionDayBottomSheetContent(
            onDismiss = {
                coroutineScope.launch { sheetState.hide() }
                    .invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismiss()
                        }
                    }
            },
            emotionDay = emotionDay,
        )
    }
}

@Composable
private fun EmotionDayBottomSheetContent(
    onDismiss: () -> Unit,
    emotionDay: SummaryEmotionDayUiModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = emotionDay.dayText,
                style = BitnagilTheme.typography.title3SemiBold,
                color = BitnagilTheme.colors.coolGray10,
                maxLines = 1,
                modifier = Modifier
                    .padding(horizontal = 24.dp),
            )

            BitnagilIconButton(
                id = R.drawable.ic_close,
                onClick = onDismiss,
                paddingValues = PaddingValues(12.dp),
                tint = BitnagilTheme.colors.coolGray10,
                modifier = Modifier.size(48.dp),
            )
        }

        Text(
            emotionDay.emotionText,
            style = BitnagilTheme.typography.body2Medium,
            color = BitnagilTheme.colors.coolGray40,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(start = 24.dp, end = 24.dp, bottom = 18.dp)
                .background(
                    color = BitnagilTheme.colors.white,
                    shape = RoundedCornerShape(12.dp),
                ),
        ) {
            Text(
                emotionDay.emotionType.displayName,
                style = BitnagilTheme.typography.body2SemiBold,
                color = emotionDay.emotionType.textColor,
                maxLines = 1,
                modifier = Modifier
                    .offset(x = 12.dp, y = 12.dp)
                    .background(color = emotionDay.emotionType.backgroundColor, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 18.dp, vertical = 5.dp)
            )

            Box(
                modifier = Modifier.align(alignment = Alignment.BottomCenter),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(R.drawable.img_pomo_hand),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null,
                    modifier = Modifier
                        .width(88.dp)
                        .height(64.dp),
                )

                Box(
                    modifier = Modifier.size(130.dp)
                        .offset(y = -(28).dp)
                ) {
                    // todo - 감정 구슬 이미지
                }

                Image(
                    painter = painterResource(R.drawable.img_pomo_thumb),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .width(36.dp)
                        .height(28.dp)
                        .offset(x = (-6).dp, y = (-24).dp),
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmotionDayBottomSheetContentPreview() {
    EmotionDayBottomSheetContent(
        onDismiss = {},
        emotionDay = SummaryEmotionDayUiModel(
            date = LocalDate.now(),
            emotionType = SummaryEmotionType.SATISFACTION,
        ),
    )
}
