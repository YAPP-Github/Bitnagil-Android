package com.threegap.bitnagil.presentation.reporthistory.component.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.domain.report.model.ReportCategory
import com.threegap.bitnagil.domain.report.model.ReportStatus
import com.threegap.bitnagil.presentation.common.extension.displayTitle
import com.threegap.bitnagil.presentation.reportdetail.component.atom.ReportProcessBadge
import com.threegap.bitnagil.presentation.reporthistory.model.ReportHistoryUiModel

@Composable
fun ReportHistoryItem(
    modifier: Modifier = Modifier,
    report: ReportHistoryUiModel,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .background(color = BitnagilTheme.colors.white, shape = RoundedCornerShape(12.dp))
            .clickableWithoutRipple(onClick = { onClick(report.id) })
            .padding(vertical = 14.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            ReportProcessBadge(reportStatus = report.status)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = report.title, style = BitnagilTheme.typography.body2Medium, maxLines = 2)

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = report.category.displayTitle, style = BitnagilTheme.typography.caption1Medium.copy(color = BitnagilTheme.colors.coolGray50))

                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .background(color = BitnagilTheme.colors.coolGray90, shape = CircleShape),
                )

                Text(
                    text = report.location,
                    modifier = Modifier.weight(1f),
                    style = BitnagilTheme.typography.caption1Medium.copy(color = BitnagilTheme.colors.coolGray50),
                    maxLines = 1,
                )
            }
        }

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(report.imageUrl)
                .build(),
            modifier = Modifier
                .size(74.dp)
                .clip(shape = RoundedCornerShape(9.dp))
                .background(color = BitnagilTheme.colors.black),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
    }
}

@Composable
@Preview
private fun ReportHistoryItemPreview() {
    BitnagilTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ReportHistoryItem(
                report = ReportHistoryUiModel(
                    id = "1",
                    title = "다이소 덕소점 앞 가로등 심해요",
                    imageUrl = "-",
                    location = "서울특별시 성북구 안암로 106",
                    status = ReportStatus.PENDING,
                    category = ReportCategory.TRANSPORTATION,
                ),
                onClick = {},
            )

            ReportHistoryItem(
                report = ReportHistoryUiModel(
                    id = "1",
                    title = "다이소 덕소점 앞 가로등 깜빡이는데 어쩌죠 이거 진짜로 심각한 문제인데 이 뒷부분은 figma에서 짤려 보여서 임의로 채웁니다",
                    imageUrl = "-",
                    location = "서울특별시 성북구 안암로 106",
                    status = ReportStatus.IN_PROGRESS,
                    category = ReportCategory.TRANSPORTATION,
                ),
                onClick = {},
            )

            ReportHistoryItem(
                report = ReportHistoryUiModel(
                    id = "1",
                    title = "퇴근하고 싶어요",
                    imageUrl = "-",
                    location = "서울특별시 성북구 안암로 106 서울특별시 성북구 안암로 106 서울특별시 성북구 안암로 106",
                    status = ReportStatus.COMPLETED,
                    category = ReportCategory.AMENITY,
                ),
                onClick = {},
            )
        }
    }
}
