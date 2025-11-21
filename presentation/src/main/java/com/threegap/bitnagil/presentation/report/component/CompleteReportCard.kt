package com.threegap.bitnagil.presentation.report.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.domain.report.model.ReportCategory
import com.threegap.bitnagil.presentation.report.model.uiTitle

@Composable
fun CompleteReportCard(
    title: String,
    category: ReportCategory?,
    address: String?,
    content: String,
    images: List<String>,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .background(
                color = BitnagilTheme.colors.coolGray98,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(20.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text(
            text = "제보 요약정보",
            color = BitnagilTheme.colors.coolGray10,
            style = BitnagilTheme.typography.body2SemiBold,
        )

        CompleteReportCardItem(
            title = "제목",
        ) {
            Text(
                text = title,
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.body1Medium,
            )
        }

        CompleteReportCardItem(
            title = "키테고리",
        ) {
            Text(
                text = category?.uiTitle ?: "카테고리 없음",
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.body1Medium,
            )
        }

        CompleteReportCardItem(
            title = "신고 위치",
        ) {
            Text(
                text = address ?: "주소 없음",
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.body1Medium,
            )
        }

        CompleteReportCardItem(
            title = "제보 내용",
        ) {
            Text(
                text = content,
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.body1Medium,
                textAlign = TextAlign.End,
            )
        }

        CompleteReportCardItem(
            title = "제보 사진",
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.End,
            ) {
                items(images) { image ->
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(48.dp)
                            .clip(RoundedCornerShape(8.dp)),
                    )
                }
            }
        }
    }
}

@Composable
private fun CompleteReportCardItem(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            color = BitnagilTheme.colors.coolGray30,
            style = BitnagilTheme.typography.body1Medium,
            modifier = Modifier.padding(end = 12.dp),
        )
        content()
    }
}

@Preview
@Composable
private fun Preview() {
    CompleteReportCard(
        title = "어쩌구",
        category = ReportCategory.WATERFACILITY,
        address = "서울특별시 강남구 역삼동",
        content = "어쩌구 저쩌구",
        images = listOf(),
    )
}
