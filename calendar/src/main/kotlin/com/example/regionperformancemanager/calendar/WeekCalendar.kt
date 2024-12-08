package com.example.regionperformancemanager.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.template.core.designsystem.component.AppBackground
import com.example.template.core.designsystem.theme.AppTheme
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.WeekDayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.LocalDate


@Composable
fun WeekCalendar(
    weekState: WeekCalendarState,
    selections: List<LocalDate>,
    onDayTouch: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
){
    WeekCalendar(
        state = weekState,
        dayContent = { day ->
            val isSelectable = day.position == WeekDayPosition.RangeDate
            Day(
                day.date,
                isSelected = isSelectable && selections.contains(day.date),
                isSelectable = isSelectable,
                onClick = onDayTouch
            )
        },
        modifier = modifier,
    )
}


@Preview
@Composable
fun WeekCalendarPreview(){

    val currentDate = remember { LocalDate.now() }
    val daysOfWeek = daysOfWeek()


    val weekState = rememberWeekCalendarState(
        startDate = currentDate,
        endDate = currentDate,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = daysOfWeek.first(),
    )
    AppTheme {
        AppBackground {
            WeekCalendar(
                weekState = weekState,
                selections = listOf(LocalDate.now()),
                onDayTouch =  {},
            )
        }
    }
}