package com.threegap.bitnagil.presentation.screen.summary.component.template.summarycalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.presentation.screen.summary.model.SummaryEmotionCellUiModel
import com.threegap.bitnagil.presentation.screen.summary.model.SummaryEmotionType
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun SummaryCalendarView(
    yearMonth: YearMonth,
    emotionDays: List<SummaryEmotionCellUiModel>,
    modifier: Modifier = Modifier,
    firstDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY,
    onClickOtherMonthDay: (YearMonth) -> Unit = {},
    onClickEmotionDay: (LocalDate, SummaryEmotionCellUiModel) -> Unit = { _, _ -> },
) {
    val firstDayOfMonth = yearMonth.atDay(1)

    // 시작 요일에 맞춰 이전 달의 며칠을 가져올지 계산
    val firstDayOfWeekValue = firstDayOfWeek.value
    val daysFromPrevMonth = (firstDayOfMonth.dayOfWeek.value - firstDayOfWeekValue + 7) % 7
    val startDate = firstDayOfMonth.minusDays(daysFromPrevMonth.toLong())

    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            val dayOfWeekOrder = List(7) { i ->
                DayOfWeek.of((firstDayOfWeekValue + i - 1) % 7 + 1)
            }

            dayOfWeekOrder.forEach { dayOfWeek ->
                Text(
                    text = dayNames[dayOfWeek] ?: "",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = BitnagilTheme.typography.body2Medium,
                    color = BitnagilTheme.colors.coolGray40
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        repeat(6) { weekIndex ->
            Row(modifier = Modifier.fillMaxWidth()) {
                repeat(7) { dayIndex ->
                    val date = startDate.plusDays((weekIndex * 7 + dayIndex).toLong())
                    val isCurrentMonth = date.monthValue == yearMonth.monthValue
                    val emotionCellUiModel = emotionDays.firstOrNull { it.date == date }

                    // 이전/다음 달 날짜는 해당 달로 이동하고, 이번 달 날짜는 감정이 기록된 경우에만 선택할 수 있다.
                    val onClick: (() -> Unit)? = when {
                        !isCurrentMonth -> {
                            { onClickOtherMonthDay(YearMonth.from(date)) }
                        }
                        emotionCellUiModel != null -> {
                            { onClickEmotionDay(date, emotionCellUiModel) }
                        }
                        else -> null
                    }

                    SummaryCalendarCell(
                        isCurrentMonth = isCurrentMonth,
                        emotionType = emotionCellUiModel?.emotionType,
                        day = date.dayOfMonth,
                        onClick = onClick,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

private val dayNames = mapOf(
    DayOfWeek.MONDAY to "월",
    DayOfWeek.TUESDAY to "화",
    DayOfWeek.WEDNESDAY to "수",
    DayOfWeek.THURSDAY to "목",
    DayOfWeek.FRIDAY to "금",
    DayOfWeek.SATURDAY to "토",
    DayOfWeek.SUNDAY to "일",
)

@Composable
fun SummaryCalendarCell(
    isCurrentMonth: Boolean,
    emotionType: SummaryEmotionType?,
    day: Int,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .then(if (onClick != null) Modifier.clickableWithoutRipple(onClick = onClick) else Modifier),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .background(
                    color = emotionType?.let { if (isCurrentMonth) it.backgroundColor else BitnagilTheme.colors.coolGray98 } ?: Color.Transparent,
                    shape = CircleShape,
                ),
        )

        Text(
            text = day.toString(),
            style = BitnagilTheme.typography.body1Medium,
            color = if (isCurrentMonth) emotionType?.textColor ?: BitnagilTheme.colors.coolGray10 else BitnagilTheme.colors.coolGray80,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SummaryCalendarPreview() {
    val currentMonth = YearMonth.now()
    val prevMonth = currentMonth.minusMonths(1)
    val nextMonth = currentMonth.plusMonths(1)
    BitnagilTheme {
        SummaryCalendarView(
            yearMonth = currentMonth,
            emotionDays = listOf(
                SummaryEmotionCellUiModel(prevMonth.atDay(30), SummaryEmotionType.CALM, ""),
                SummaryEmotionCellUiModel(currentMonth.atDay(6), SummaryEmotionType.CALM, ""),
                SummaryEmotionCellUiModel(currentMonth.atDay(8), SummaryEmotionType.ANXIETY, ""),
                SummaryEmotionCellUiModel(currentMonth.atDay(9), SummaryEmotionType.VITALITY, ""),
                SummaryEmotionCellUiModel(currentMonth.atDay(11), SummaryEmotionType.LETHARGY, ""),
                SummaryEmotionCellUiModel(currentMonth.atDay(14), SummaryEmotionType.SATISFACTION, ""),
                SummaryEmotionCellUiModel(currentMonth.atDay(16), SummaryEmotionType.FATIGUE, ""),
                SummaryEmotionCellUiModel(nextMonth.atDay(1), SummaryEmotionType.FATIGUE, ""),
            ),
            firstDayOfWeek = DayOfWeek.SUNDAY
        )
    }
}
