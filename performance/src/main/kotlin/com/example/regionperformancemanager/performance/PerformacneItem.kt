package com.example.regionperformancemanager.performance

import android.content.Context
import android.net.Uri
import androidx.annotation.ColorInt
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.regionperformancemanager.performance.model.Performance
import com.example.template.core.designsystem.component.AppBackground
import com.example.template.core.designsystem.component.AppIconToggleButton
import com.example.template.core.designsystem.icon.AppIcons
import com.example.template.core.designsystem.theme.AppTheme

@Composable
fun PerformanceItem(
    performance: Performance,
    onClick: () -> Unit,
    onBookmarkChange: (change: Boolean) -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)){
            Row{
                Text(
                    performance.eventName,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .fillMaxWidth((.8f))
                )


                Spacer(modifier = Modifier.weight(1f))

                AppIconToggleButton(
                    checked = true,
                    onCheckedChange = onBookmarkChange,
                    enabled = true,
                    icon = { Icon(imageVector = AppIcons.BookmarkBorder, contentDescription = null) },
                    checkedIcon = { Icon(imageVector = AppIcons.Bookmark, contentDescription = null)},
                )
            }
            Spacer(Modifier.height(4.dp))


            Column(modifier = Modifier.fillMaxWidth(.8f)){
                Text(text = performance.location, style = MaterialTheme.typography.labelLarge)
                Text(text = performance.startDate + "-" + performance.endDate,  style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

fun launchCustomChromeTab(context: Context, uri: Uri, @ColorInt toolbarColor: Int) {
    val customTabBarColor = CustomTabColorSchemeParams.Builder()
        .setToolbarColor(toolbarColor).build()
    val customTabsIntent = CustomTabsIntent.Builder()
        .setDefaultColorSchemeParams(customTabBarColor)
        .build()
    customTabsIntent.launchUrl(context, uri)
}

@Preview
@Composable
fun PerformanceItemPreview(){
    AppTheme {
        AppBackground {
            PerformanceItem(
                performance = Performance(
                    id = 1,
                    regionId = 101,
                    address = "경상북도 경주시 알천북로 1",
                    admissionFee = "Free",
                    ageLimit = "초등학생이상관람가능",
                    chargeInfo = "R석 50000원+S석 40000원+시야제한석 20000원",
                    endDate = "2024-12-31",
                    endTime = "22:00",
                    eventName = "한수원 문화가 있는 날 2024 신년음악회 필하모닉 앙상블(빈)",
                    homePageUrl = "https://www.artgala2024.com",
                    latitude = 37.7749,
                    location = "경주예술의전당(화랑홀)",
                    longitude = -122.4194,
                    seatNumber = "0",
                    startDate = "2024-12-01",
                    startTime = "18:00"
                ),
                onClick = {},
                onBookmarkChange = {}
            )
        }
    }
}
