package com.example.regionperformancemanager.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.regionperformancemanager.calendar.utils.circleBackground
import com.example.regionperformancemanager.calendar.utils.clickable
import com.example.template.core.designsystem.component.AppBackground
import com.example.template.core.designsystem.component.ThemePreviews
import com.example.template.core.designsystem.theme.AppTheme
import java.time.LocalDate


@Composable
fun Day(
    day: LocalDate,
    isSelected: Boolean = false,
    isSelectable: Boolean = true,
    onClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {

    val isToday: Boolean = (day == LocalDate.now())

    val textColor: Color = when {
        isSelected -> Color.White
        isSelectable -> Color.Unspecified
        else -> if(isToday) Color.White else Color.LightGray
    }

    val backgroundColor: Color = if(isSelected){
        Color.Red
    }else if(isToday){
        Color.Yellow
    }else{
        Color.Transparent
    }


    Box(
        modifier = modifier
            .aspectRatio(1f),
            contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier
                .circleBackground(color = backgroundColor, padding = 3.dp)
                .clickable(
                    enabled = isSelectable,
                    showRipple = !isSelected,
                    onClick = { onClick(day) },
                ),
            text = day.dayOfMonth.toString(),
            color = textColor,
            fontSize = 14.sp,
        )
    }
}



@ThemePreviews()
@Composable
fun DayPreview(){
    AppTheme {
        AppBackground(Modifier.size(120.dp)){
            Day(
                day = LocalDate.now(),
                isSelected = true,
                onClick = {}
            )
        }
    }
}

@ThemePreviews()
@Composable
fun SelectedDayPreview(){
    AppTheme {
        AppBackground(Modifier.size(120.dp)){
            Day(
                day = LocalDate.now(),
                isSelected = true,
                onClick = {}
            )
        }
    }
}
