package com.example.regionperformancemanager.calendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.template.core.designsystem.component.AppBackground
import com.example.template.core.designsystem.theme.AppTheme
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.yearMonth
import java.time.LocalDate


@Composable
fun MonthCalendar(
    state: CalendarState,
    selections: List<LocalDate>,
    onDayTouch: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
){
    HorizontalCalendar(
        modifier = modifier,
        state = state,
        dayContent = { day ->
            val isSelectable = day.position == DayPosition.MonthDate
            Day(
                day.date,
                isSelected = isSelectable && selections.contains(day.date),
                isSelectable = isSelectable,
                onClick = onDayTouch
            )
        }
    )
}



@Preview
@Composable
fun MonthCalendarPreview(){

    val currentDate = LocalDate.now()
    val currentMonth = currentDate.yearMonth
    val daysOfWeek = daysOfWeek()

    val monthState = rememberCalendarState(
        startMonth = LocalDate.now().yearMonth,
        endMonth = LocalDate.now().yearMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    AppTheme {
        AppBackground {
            MonthCalendar(
                state = monthState,
                selections =  emptyList(),
                onDayTouch = {}
            )
        }
    }
}

