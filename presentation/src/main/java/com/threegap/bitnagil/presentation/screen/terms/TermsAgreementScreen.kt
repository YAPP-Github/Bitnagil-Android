package com.threegap.bitnagil.presentation.screen.terms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.presentation.screen.terms.component.TermsAgreementItem
import com.threegap.bitnagil.presentation.screen.terms.component.ToggleAllAgreementsItem
import com.threegap.bitnagil.presentation.screen.terms.contract.TermsAgreementSideEffect
import com.threegap.bitnagil.presentation.screen.terms.contract.TermsAgreementState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun TermsAgreementScreenContainer(
    viewModel: TermsAgreementViewModel = hiltViewModel(),
    navigateToTermsOfService: () -> Unit,
    navigateToPrivacyPolicy: () -> Unit,
    navigateToOnBoarding: () -> Unit,
    navigateToBack: () -> Unit,
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is TermsAgreementSideEffect.NavigateToPrivacyPolicy -> navigateToPrivacyPolicy()
            is TermsAgreementSideEffect.NavigateToTermsOfService -> navigateToTermsOfService()
            is TermsAgreementSideEffect.NavigateToOnBoarding -> navigateToOnBoarding()
            is TermsAgreementSideEffect.NavigateToBack -> navigateToBack()
        }
    }

    TermsAgreementScreen(
        uiState = uiState,
        onToggleAllAgreements = viewModel::updateAllAgreements,
        onToggleTermsOfService = viewModel::updateTermsOfService,
        onTogglePrivacyPolicy = viewModel::updatePrivacyPolicy,
        onToggleOverFourteen = viewModel::updateOverFourteen,
        onShowTermsOfService = viewModel::navigateToTermsOfService,
        onShowPrivacyPolicy = viewModel::navigateToPrivacyPolicy,
        onStartButtonClick = viewModel::submitTermsAgreement,
        onBackButtonClick = viewModel::navigateToBack,
    )
}

@Composable
private fun TermsAgreementScreen(
    uiState: TermsAgreementState,
    onToggleAllAgreements: (Boolean) -> Unit,
    onToggleTermsOfService: () -> Unit,
    onTogglePrivacyPolicy: () -> Unit,
    onToggleOverFourteen: () -> Unit,
    onShowTermsOfService: () -> Unit,
    onShowPrivacyPolicy: () -> Unit,
    onStartButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        BitnagilTopBar(
            title = "약관 동의",
            showBackButton = true,
            onBackClick = onBackButtonClick,
        )

        Spacer(modifier = Modifier.height(48.dp))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            Text(
                text = "빛나길 이용을 위해\n필수 약관에 동의해 주세요.",
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.title2Bold,
            )

            Spacer(modifier = Modifier.height(28.dp))

            ToggleAllAgreementsItem(
                isAllAgreed = uiState.isAllAgreed,
                onToggleAllAgreements = onToggleAllAgreements,
            )

            Spacer(modifier = Modifier.height(8.dp))

            TermsAgreementItem(
                title = "(필수) 서비스 이용약관 동의",
                onCheckedChange = { onToggleTermsOfService() },
                isChecked = uiState.agreedTermsOfService,
                showMore = true,
                onClickShowMore = onShowTermsOfService,
            )

            TermsAgreementItem(
                title = "(필수) 개인정보 수집·이용 동의",
                onCheckedChange = { onTogglePrivacyPolicy() },
                isChecked = uiState.agreedPrivacyPolicy,
                showMore = true,
                onClickShowMore = onShowPrivacyPolicy,
            )

            TermsAgreementItem(
                title = "(필수) 만 14세 이상입니다.",
                onCheckedChange = { onToggleOverFourteen() },
                isChecked = uiState.agreedOverFourteen,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        BitnagilTextButton(
            text = "다음",
            onClick = onStartButtonClick,
            enabled = uiState.submitEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 20.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TermsAgreementScreenPreview() {
    TermsAgreementScreen(
        uiState = TermsAgreementState.INIT,
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
