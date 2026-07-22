package com.threegap.bitnagil.presentation.screen.summary.component.template.summarybadge

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.presentation.screen.summary.model.BadgeImage
import com.threegap.bitnagil.presentation.screen.summary.model.SummaryBadgeItemUiModel
import com.threegap.bitnagil.presentation.screen.summary.model.SummaryBadgeTypeUiModel
import com.threegap.bitnagil.presentation.screen.summary.model.SummaryBadgeUiModel
import com.threegap.bitnagil.presentation.util.dimension.pxToDp

@Composable
fun SummaryBadgeView(
    summaryBadge: SummaryBadgeUiModel?,
    modifier: Modifier = Modifier
) {
    Background(
        modifier = modifier.clipToBounds()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            summaryBadge?.let {
                Text(
                    summaryBadge.badgeDescription,
                    style = BitnagilTheme.typography.cafe24SsurroundAir,
                    color = BitnagilTheme.colors.white,
                    textAlign = TextAlign.Center
                )

                SummaryBadgeListView(
                    badges = summaryBadge.badges
                )

                BadgeTitleView(
                    badgeTitle = summaryBadge.badgeTitle,
                    isBadgeReserved = summaryBadge.badges.firstOrNull()?.type?.isReserved ?: false,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
private fun Background(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    0.0f to Color(0xFFFF964B),
                    0.3f to Color(0xFFFE7120),
                ),
            )
            .statusBarsPadding()
            .height(260.dp),
    ) {
        val width = constraints.maxWidth.pxToDp()

        StarImage(
            modifier = Modifier
                .width(width * 0.55f)
                .aspectRatio(1f)
                .offset(x = (-93).dp, y = (-81).dp)
                .rotate(103f)
        )

        StarImage(
            modifier = Modifier
                .width(width * 0.13f)
                .aspectRatio(1f)
                .offset(x = 24.dp, y = 188.dp)
                .rotate(24f)
        )

        StarImage(
            modifier = Modifier
                .width(width * 0.06f)
                .aspectRatio(1f)
                .offset(x = 50.dp, y = 270.dp)
                .rotate(330f)
        )

        StarImage(
            modifier = Modifier
                .width(width * 0.83f)
                .aspectRatio(1f)
                .align(Alignment.BottomEnd)
                .offset(x = 90.dp, y = 90.dp)
                .rotate(330f),
        )

        StarImage(
            modifier = Modifier
                .width(width * 0.18f)
                .aspectRatio(1f)
                .align(Alignment.TopEnd)
                .offset(x = 30.dp, y = 50.dp)
                .rotate(342f),
            color = BitnagilTheme.colors.orange500
        )

        content()
    }
}

@Composable
private fun StarImage(
    modifier: Modifier = Modifier,
    color: Color = BitnagilTheme.colors.orange400,
    contentScale: ContentScale = ContentScale.Fit
) {
    Image(
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_shine),
        contentDescription = null,
        colorFilter = ColorFilter.tint(color),
        contentScale = contentScale,
        modifier = modifier
    )
}

@Composable
private fun SummaryBadgeListView(
    badges: List<SummaryBadgeItemUiModel>,
    modifier: Modifier = Modifier,
) {
    val isSingleItem = badges.size <= 1

    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        badges.forEach { badge ->
            if (isSingleItem)
                SummaryBadgeItemView(
                    badge = badge,
                    modifier = Modifier.size(125.dp).aspectRatio(1f)
                )
            else
                SummaryBadgeItemView(
                    badge = badge,
                    modifier = Modifier.widthIn(max = 100.dp).weight(1f, fill = false)
                )
        }
    }
}

@Composable
private fun SummaryBadgeItemView(
    badge: SummaryBadgeItemUiModel,
    modifier: Modifier = Modifier,
) {
    when (val image = badge.image) {
        BadgeImage.Default ->
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_badge_question),
                contentDescription = null,
                modifier = modifier.aspectRatio(1f).padding(9.dp)
            )

        is BadgeImage.Remote ->
            AsyncImage(
                model = image.url,
                contentDescription = null,
                modifier = modifier.aspectRatio(1f)
            )
    }
}

@Composable
private fun BadgeTitleView(
    badgeTitle: String,
    isBadgeReserved: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(color = BitnagilTheme.colors.orange700, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_shine),
            contentDescription = null,
            modifier = Modifier.size(15.dp),
            colorFilter = ColorFilter.tint(if (isBadgeReserved) BitnagilTheme.colors.coolGray95 else BitnagilTheme.colors.kakao)
        )

        Text(
            text = badgeTitle,
            style = BitnagilTheme.typography.caption1Medium,
            color = BitnagilTheme.colors.white
        )
    }
}

@Preview(showBackground = true, heightDp = 360)
@Composable
private fun SummaryBadgePreview() {
    BitnagilTheme {
        SummaryBadgeView(
            summaryBadge = SummaryBadgeUiModel(
                badgeTitle = "외출 전문가",
                badges = listOf(
                    SummaryBadgeItemUiModel(
                        type = SummaryBadgeTypeUiModel.Unknown,
                        image = BadgeImage.Default,
                        acquired = false,
                    ),
                ),
                badgeDescription = "덕분에 도시가\n개선되고 있어요!"
            ),
            modifier = Modifier
        )
    }
}
