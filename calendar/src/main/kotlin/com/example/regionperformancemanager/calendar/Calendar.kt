package com.example.regionperformancemanager.calendar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.regionperformancemanager.calendar.utils.rememberFirstVisibleMonthAfterScroll
import com.example.regionperformancemanager.calendar.utils.rememberFirstVisibleWeekAfterScroll
import com.example.template.core.designsystem.component.AppBackground
import com.example.template.core.designsystem.theme.AppTheme
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.kizitonwose.calendar.core.yearMonth
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun Calendar(
    selections: List<LocalDate>,
    onDayTouch: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {

    val adjacentMonths: Long = 500

    val currentDate = remember { LocalDate.now() }
    val currentMonth = remember(currentDate) { currentDate.yearMonth }

    val startMonth = remember(currentDate) { currentMonth.minusMonths(adjacentMonths) }
    val endMonth = remember(currentDate) { currentMonth.plusMonths(adjacentMonths) }

    val daysOfWeek = remember { daysOfWeek() }

    var isWeekMode by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()


    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
    ) {
        val monthState = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first(),
        )
        val weekState = rememberWeekCalendarState(
            startDate = startMonth.atStartOfMonth(),
            endDate = endMonth.atEndOfMonth(),
            firstVisibleWeekDate = currentDate,
            firstDayOfWeek = daysOfWeek.first(),
        )

        fun scroll(date: LocalDate){
            coroutineScope.launch {
                if (isWeekMode){
                    weekState.scrollToWeek(date)
                }else{
                    val month =  YearMonth.of(date.year, date.month)
                    monthState.scrollToMonth(month)
                }
            }
        }

        val visibleMonth = rememberFirstVisibleMonthAfterScroll(monthState)
        val visibleWeek = rememberFirstVisibleWeekAfterScroll(weekState)

        CalendarTitle(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
            currentMonth =  if (isWeekMode) visibleWeek.days.first().date.yearMonth else visibleMonth.yearMonth,
            goToPrevious = {
                coroutineScope.launch {
                    if (isWeekMode) {
                        val targetDate = weekState.firstVisibleWeek.days.first().date.minusDays(1)
                        weekState.animateScrollToWeek(targetDate)
                    } else {
                        val targetMonth = monthState.firstVisibleMonth.yearMonth.previousMonth
                        monthState.animateScrollToMonth(targetMonth)
                    }
                }
            },
            goToNext = {
                coroutineScope.launch {
                    if (isWeekMode) {
                        val targetDate = weekState.firstVisibleWeek.days.last().date.plusDays(1)
                        weekState.animateScrollToWeek(targetDate)
                    } else {
                        val targetMonth = monthState.firstVisibleMonth.yearMonth.nextMonth
                        monthState.animateScrollToMonth(targetMonth)
                    }
                }
            }
        )

        CalendarHeader(daysOfWeek = daysOfWeek)

        CalendarBody(
            isWeekMode = isWeekMode,
            visibleMonth = visibleMonth,
            monthState = monthState,
            weekState = weekState,
            selections = selections,
            onDayTouch = onDayTouch
        )
    }
}

@Composable
fun CalendarBody(
    isWeekMode: Boolean,
    visibleMonth: CalendarMonth,
    monthState: CalendarState,
    weekState: WeekCalendarState,
    selections: List<LocalDate>,
    onDayTouch: (LocalDate) -> Unit
){
    val monthCalendarAlpha by animateFloatAsState(if (isWeekMode) 0f else 1f)
    val weekCalendarAlpha by animateFloatAsState(if (isWeekMode) 1f else 0f)
    var weekCalendarSize by remember { mutableStateOf(DpSize.Zero) }
    val weeksInVisibleMonth = visibleMonth.weekDays.count()
    val monthCalendarHeight by animateDpAsState(
        if (isWeekMode) {
            weekCalendarSize.height
        } else {
            weekCalendarSize.height * weeksInVisibleMonth
        },
        tween(durationMillis = 250),
    )
    val density = LocalDensity.current

    Box {
        MonthCalendar(
            state = monthState,
            selections = selections,
            onDayTouch = onDayTouch,
            modifier = Modifier
                .height(monthCalendarHeight)
                .alpha(monthCalendarAlpha)
                .zIndex(if (isWeekMode) 0f else 1f),

        )

        WeekCalendar(
            weekState = weekState,
            selections = selections,
            onDayTouch =  onDayTouch,
            modifier = Modifier
                .wrapContentHeight()
                .onSizeChanged {
                    val size = density.run { DpSize(it.width.toDp(), it.height.toDp()) }
                    if (weekCalendarSize != size) {
                        weekCalendarSize = size
                    }
                }
                .alpha(weekCalendarAlpha)
                .zIndex(if (isWeekMode) 1f else 0f),
        )
    }
}



@Preview
@Composable
private fun CalendarPreview() {
    AppTheme {
        AppBackground {
            Calendar(
                selections = listOf(LocalDate.now()),
                onDayTouch = { }
            )
        }
    }

}