package com.threegap.bitnagil.presentation.terms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.threegap.bitnagil.presentation.terms.component.TermsAgreementItem
import com.threegap.bitnagil.presentation.terms.model.TermsAgreementIntent
import com.threegap.bitnagil.presentation.terms.model.TermsAgreementSideEffect
import com.threegap.bitnagil.presentation.terms.model.TermsAgreementState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun TermsAgreementScreenContainer(
    navigateToOnBoarding: () -> Unit,
    navigateToBack: () -> Unit,
    viewmodel: TermsAgreementViewModel = hiltViewModel(),
) {
    val uiState by viewmodel.stateFlow.collectAsStateWithLifecycle()

    viewmodel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is TermsAgreementSideEffect.NavigateToOnBoarding -> {
                navigateToOnBoarding()
            }

            is TermsAgreementSideEffect.NavigateToBack -> {
                navigateToBack()
            }
        }
    }

    TermsAgreementScreen(
        uiState = uiState,
        onToggleAllAgreements = {
            viewmodel.sendIntent(TermsAgreementIntent.ToggleAllAgreements(it))
        },
        onToggleTermsOfService = {
            viewmodel.sendIntent(TermsAgreementIntent.ToggleTermsOfService(it))
        },
        onTogglePrivacyPolicy = {
            viewmodel.sendIntent(TermsAgreementIntent.TogglePrivacyPolicy(it))
        },
        onToggleOverFourteen = {
            viewmodel.sendIntent(TermsAgreementIntent.ToggleOverFourteen(it))
        },
        onStartButtonClick = {
            viewmodel.submitTermsAgreement()
        },
        onBackButtonClick = {
            viewmodel.sendIntent(TermsAgreementIntent.BackButtonClick)
        },
    )
}

@Composable
private fun TermsAgreementScreen(
    uiState: TermsAgreementState,
    onToggleAllAgreements: (Boolean) -> Unit,
    onToggleTermsOfService: (Boolean) -> Unit,
    onTogglePrivacyPolicy: (Boolean) -> Unit,
    onToggleOverFourteen: (Boolean) -> Unit,
    onStartButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "뒤로가기",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable(
                        onClick = onBackButtonClick,
                    ),
            )

            Text(
                text = "이용약관",
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center),
            )
        }

        Column(
            modifier = Modifier
                .padding(
                    top = 48.dp,
                )
                .padding(horizontal = 16.dp),
        ) {
            Text(
                text = "빛나길 이용을 위해\n필수 약관에 동의해 주세요.",
            )

            Spacer(
                modifier = Modifier.height(28.dp),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(10.dp),
                    )
                    .clickable { onToggleAllAgreements(!uiState.isAllAgreed) }
                    .padding(
                        vertical = 12.dp,
                        horizontal = 16.dp,
                    ),
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = if (uiState.isAllAgreed) Color.Black else Color.Gray,
                    modifier = Modifier.size(24.dp),
                )
                Text(
                    text = "전체동의",
                )
            }

            TermsAgreementItem(
                title = "(필수) 서비스 이용약관 동의",
                onCheckedChange = { onToggleTermsOfService(!uiState.agreedTermsOfService) },
                isChecked = uiState.agreedTermsOfService,
                showMore = true,
            )

            TermsAgreementItem(
                title = "(필수) 개인정보 수집·이용 동의",
                onCheckedChange = { onTogglePrivacyPolicy(!uiState.agreedPrivacyPolicy) },
                isChecked = uiState.agreedPrivacyPolicy,
                showMore = true,
            )

            TermsAgreementItem(
                title = "(필수) 만 14세 이상입니다.",
                onCheckedChange = { onToggleOverFourteen(!uiState.agreedOverFourteen) },
                isChecked = uiState.agreedOverFourteen,
            )
        }

        Button(
            onClick = onStartButtonClick,
            enabled = uiState.isAllAgreed,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp,
                ),
        ) {
            Text("시작하기")
        }
    }
}

@Preview
@Composable
private fun TermsAgreementScreenPreview() {
    TermsAgreementScreen(
        uiState = TermsAgreementState(),
        onToggleAllAgreements = {},
        onToggleTermsOfService = {},
        onTogglePrivacyPolicy = {},
        onToggleOverFourteen = {},
        onStartButtonClick = {},
        onBackButtonClick = {},
    )
}
