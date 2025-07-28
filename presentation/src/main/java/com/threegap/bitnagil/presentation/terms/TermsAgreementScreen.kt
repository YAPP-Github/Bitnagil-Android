package com.threegap.bitnagil.presentation.terms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.presentation.terms.component.TermsAgreementItem
import com.threegap.bitnagil.presentation.terms.component.ToggleAllAgreementsItem
import com.threegap.bitnagil.presentation.terms.model.TermsAgreementIntent
import com.threegap.bitnagil.presentation.terms.model.TermsAgreementSideEffect
import com.threegap.bitnagil.presentation.terms.model.TermsAgreementState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun TermsAgreementScreenContainer(
    navigateToTermsOfService: () -> Unit,
    navigateToPrivacyPolicy: () -> Unit,
    navigateToOnBoarding: () -> Unit,
    navigateToBack: () -> Unit,
    viewmodel: TermsAgreementViewModel = hiltViewModel(),
) {
    val uiState by viewmodel.stateFlow.collectAsStateWithLifecycle()

    viewmodel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is TermsAgreementSideEffect.NavigateToPrivacyPolicy -> {
                navigateToPrivacyPolicy()
            }

            is TermsAgreementSideEffect.NavigateToTermsOfService -> {
                navigateToTermsOfService()
            }

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
        onShowTermsOfService = {
            viewmodel.sendIntent(TermsAgreementIntent.ShowTermsOfService)
        },
        onShowPrivacyPolicy = {
            viewmodel.sendIntent(TermsAgreementIntent.ShowPrivacyPolicy)
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
    onShowTermsOfService: () -> Unit,
    onShowPrivacyPolicy: () -> Unit,
    onStartButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BitnagilTheme.colors.white),
    ) {
        BitnagilTopBar(
            title = "약관 동의",
            showBackButton = true,
            onBackClick = onBackButtonClick
        )

        Spacer(modifier = Modifier.height(48.dp))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            Text(
                text = "빛나길 이용을 위해\n필수 약관에 동의해 주세요.",
                color = BitnagilTheme.colors.navy500,
                style = BitnagilTheme.typography.title2Bold
            )

            Spacer(modifier = Modifier.height(28.dp))

            ToggleAllAgreementsItem(
                isAllAgreed = uiState.isAllAgreed,
                onToggleAllAgreements = onToggleAllAgreements
            )

            Spacer(modifier = Modifier.height(8.dp))

            TermsAgreementItem(
                title = "(필수) 서비스 이용약관 동의",
                onCheckedChange = { onToggleTermsOfService(!uiState.agreedTermsOfService) },
                isChecked = uiState.agreedTermsOfService,
                showMore = true,
                onClickShowMore = onShowTermsOfService,
            )

            TermsAgreementItem(
                title = "(필수) 개인정보 수집·이용 동의",
                onCheckedChange = { onTogglePrivacyPolicy(!uiState.agreedPrivacyPolicy) },
                isChecked = uiState.agreedPrivacyPolicy,
                showMore = true,
                onClickShowMore = onShowPrivacyPolicy,
            )

            TermsAgreementItem(
                title = "(필수) 만 14세 이상입니다.",
                onCheckedChange = { onToggleOverFourteen(!uiState.agreedOverFourteen) },
                isChecked = uiState.agreedOverFourteen,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        BitnagilTextButton(
            text = "시작하기",
            onClick = onStartButtonClick,
            enabled = uiState.submitEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 20.dp)
        )
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
        onShowTermsOfService = {},
        onShowPrivacyPolicy = {},
        onStartButtonClick = {},
        onBackButtonClick = {},
    )
}
