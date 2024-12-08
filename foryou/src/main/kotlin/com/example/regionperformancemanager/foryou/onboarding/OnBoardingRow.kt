package com.example.regionperformancemanager.foryou.onboarding

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.example.regionperformancemanager.region.RegionsIcon
import com.example.template.core.designsystem.component.AppIconToggleButton
import com.example.template.core.designsystem.component.scrollbar.DecorativeScrollbar
import com.example.template.core.designsystem.component.scrollbar.scrollbarState
import com.example.template.core.designsystem.icon.AppIcons


@Composable
internal fun OnBoardingContent(
    onboardingUiState: OnboardingUiState.Shown,
    onFollowButtonClick: (com.example.regionperformancemanager.region.model.Region, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyGridState = rememberLazyGridState()

    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        LazyHorizontalGrid(
            state = lazyGridState,
            rows = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(24.dp),
            modifier = Modifier
                .heightIn(max = max(240.dp, with(LocalDensity.current) { 240.sp.toDp() }))
                .fillMaxWidth()
        ) {
            items(
                items = onboardingUiState.regions,
                key = { it.region.id },
            ) {
                InterestRow(
                    name = it.region.name,
                    imageUrl = it.region.imageUrl,
                    isSelected = it.isFollowing,
                    onClick = { onClick -> onFollowButtonClick(it.region, onClick) },
                )
            }
        }
        lazyGridState.DecorativeScrollbar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .align(Alignment.BottomStart),
            state = lazyGridState.scrollbarState(itemsAvailable = onboardingUiState.regions.size),
            orientation = Orientation.Horizontal,
        )
    }
}

@Composable
private fun InterestRow(
    name: String,
    imageUrl: String,
    isSelected: Boolean,
    onClick: (Boolean) -> Unit,
) {
    Surface(
        modifier = Modifier
            .width(312.dp)
            .heightIn(min = 56.dp),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        color = MaterialTheme.colorScheme.surface,
        selected = isSelected,
        onClick = {
            onClick(!isSelected)
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 12.dp, end = 8.dp),
        ) {
            RegionsIcon(
                imageUrl = imageUrl,
                modifier = Modifier
                    .padding(10.dp)
                    .size(32.dp)
            )
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .weight(1f),
                color = MaterialTheme.colorScheme.onSurface,
            )
            AppIconToggleButton(
                checked = isSelected,
                onCheckedChange = { checked -> onClick(checked) },
                icon = {
                    Icon(
                        imageVector = AppIcons.Add,
                        contentDescription = name,
                    )
                },
                checkedIcon = {
                    Icon(
                        imageVector = AppIcons.Check,
                        contentDescription = name,
                    )
                },
            )
        }
    }
}
