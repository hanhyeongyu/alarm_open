package com.example.regionperformancemanager.interest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.regionperformancemanager.region.RegionsIcon
import com.example.template.core.designsystem.component.AppIconToggleButton
import com.example.template.core.designsystem.icon.AppIcons
import com.example.template.core.designsystem.theme.AppTheme


@Composable
fun InterestsItem(
    name: String,
    description: String = "",
    imageUrl: String?,
    isFollowing: Boolean,
    onClick: () -> Unit,
    onFollowButtonClick: (Boolean) -> Unit,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    ) {
    ListItem(
        leadingContent = {
            if(!imageUrl.isNullOrEmpty()){
                RegionsIcon(imageUrl, iconModifier.size(48.dp))
            }
        },
        headlineContent = {
            Text(text = name)
        },
        supportingContent = {
            Text(text = description)
        },
        trailingContent = {
            AppIconToggleButton(
                checked = isFollowing,
                onCheckedChange = onFollowButtonClick,
                icon = {
                    Icon(
                        imageVector = AppIcons.Add,
                        contentDescription = null,
                    )
                },
                checkedIcon = {
                    Icon(
                        imageVector = AppIcons.Check,
                        contentDescription = null,
                    )
                },
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.surfaceVariant
            } else {
                Color.Transparent
            },
        ),
        modifier = modifier
            .semantics(mergeDescendants = true) {
                selected = isSelected
            }
            .clickable(enabled = true, onClick = onClick),
    )
}




@Preview
@Composable
private fun InterestsCardPreview() {
    AppTheme {
        Surface {
            InterestsItem(
                name = "Compose",
                description = "Description",
                imageUrl = "",
                isFollowing = false,
                onClick = { },
                onFollowButtonClick = { },
            )
        }
    }
}

@Preview
@Composable
private fun InterestsCardLongNamePreview() {
    AppTheme {
        Surface {
            InterestsItem(
                name = "This is a very very very very long name",
                description = "Description",
                imageUrl = "",
                isFollowing = true,
                onClick = { },
                onFollowButtonClick = { },
            )
        }
    }
}

@Preview
@Composable
private fun InterestsCardLongDescriptionPreview() {
    AppTheme {
        Surface {
            InterestsItem(
                name = "Compose",
                description = "This is a very very very very very very very " +
                        "very very very long description",
                imageUrl = "",
                isFollowing = false,
                onClick = { },
                onFollowButtonClick = { },
            )
        }
    }
}

@Preview
@Composable
private fun InterestsCardWithEmptyDescriptionPreview() {
    AppTheme{
        Surface {
            InterestsItem(
                name = "Compose",
                description = "",
                imageUrl = "",
                isFollowing = true,
                onClick = { },
                onFollowButtonClick = { },
            )
        }
    }
}

@Preview
@Composable
private fun InterestsCardSelectedPreview() {
    AppTheme {
        Surface {
            InterestsItem(
                name = "Compose",
                description = "",
                imageUrl = "",
                isFollowing = true,
                onClick = { },
                onFollowButtonClick = { },
                isSelected = true,
            )
        }
    }
}
