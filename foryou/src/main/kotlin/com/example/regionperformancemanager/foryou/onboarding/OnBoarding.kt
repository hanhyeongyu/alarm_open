package com.example.regionperformancemanager.foryou.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.regionperformancemanager.foryou.R
import com.example.template.core.designsystem.component.AppButton

@Composable
fun OnBoarding(
    onboardingUiState: OnboardingUiState.Shown,
    onFollowButtonClick: (com.example.regionperformancemanager.region.model.Region, Boolean) -> Unit,
    saveFollowedRegions: () -> Unit,
    interestsItemModifier: Modifier = Modifier,
) {
    Column(modifier = interestsItemModifier) {
        Text(
            text = stringResource(R.string.forYou_onboarding_title),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = stringResource(R.string.forYou_onboarding_subTitle),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 24.dp, end = 24.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
        OnBoardingContent(
            onboardingUiState,
            onFollowButtonClick,
            Modifier.padding(bottom = 8.dp),
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            AppButton(
                onClick = saveFollowedRegions,
                enabled = onboardingUiState.isDismissable,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .widthIn(364.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(R.string.forYou_onboarding_submit),
                )
            }
        }
    }
}