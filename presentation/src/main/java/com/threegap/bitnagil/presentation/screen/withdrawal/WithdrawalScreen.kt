package com.threegap.bitnagil.presentation.screen.withdrawal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon
import com.threegap.bitnagil.designsystem.component.atom.BitnagilSelectButton
import com.threegap.bitnagil.designsystem.component.atom.BitnagilSelectButtonColor
import com.threegap.bitnagil.designsystem.component.atom.BitnagilTextButton
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.presentation.screen.withdrawal.component.WithdrawalConfirmDialog
import com.threegap.bitnagil.presentation.screen.withdrawal.contract.WithdrawalSideEffect
import com.threegap.bitnagil.presentation.screen.withdrawal.contract.WithdrawalState
import com.threegap.bitnagil.presentation.screen.withdrawal.model.WithdrawalReason
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun WithdrawalScreenContainer(
    viewModel: WithdrawalViewModel = hiltViewModel(),
    navigateToBack: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is WithdrawalSideEffect.NavigateToBack -> navigateToBack()
            is WithdrawalSideEffect.NavigateToLogin -> navigateToLogin()
        }
    }

    if (uiState.showSuccessDialog) {
        WithdrawalConfirmDialog(
            onConfirm = viewModel::navigateToLogin,
        )
    }

    WithdrawalScreen(
        uiState = uiState,
        onTermsToggle = viewModel::onTermsToggle,
        onReasonSelect = viewModel::updateSelectedReason,
        onCustomReasonChanged = viewModel::updateCustomReason,
        onBackClick = viewModel::navigateToBack,
        onWithdrawalClick = viewModel::withdrawal,
    )
}

@Composable
private fun WithdrawalScreen(
    uiState: WithdrawalState,
    onTermsToggle: () -> Unit,
    onReasonSelect: (WithdrawalReason?) -> Unit,
    onCustomReasonChanged: (String) -> Unit,
    onBackClick: () -> Unit,
    onWithdrawalClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .windowInsetsPadding(WindowInsets.ime),
    ) {
        BitnagilTopBar(
            title = "탈퇴하기",
            showBackButton = true,
            onBackClick = onBackClick,
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)
                .verticalScroll(scrollState),
        ) {
            Spacer(modifier = Modifier.height(46.dp))

            Text(
                text = "정말 탈퇴하시겠어요?",
                color = BitnagilTheme.colors.coolGray10,
                style = BitnagilTheme.typography.title3SemiBold,
                modifier = Modifier.padding(bottom = 5.dp),
            )

            Text(
                text = "탈퇴하면 보관 중인 데이터와 서비스 이용 내역이\n모두 삭제되고, 다시 가입해도 복구되지 않아요.",
                color = BitnagilTheme.colors.coolGray50,
                style = BitnagilTheme.typography.body1Medium,
            )

            Spacer(modifier = Modifier.height(26.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableWithoutRipple { onTermsToggle() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                BitnagilIcon(
                    id = if (uiState.isTermsChecked) R.drawable.ic_check_circle else R.drawable.ic_check_default,
                    tint = null,
                )

                Text(
                    text = "유의사항을 확인했어요.",
                    color = BitnagilTheme.colors.coolGray40,
                    style = BitnagilTheme.typography.body2Medium,
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Column(
                modifier = Modifier
                    .alpha(if (uiState.isTermsChecked) 1f else 0f),
            ) {
                Text(
                    text = "탈퇴 사유를 알려주실 수 있나요?",
                    color = BitnagilTheme.colors.coolGray10,
                    style = BitnagilTheme.typography.title3SemiBold,
                    modifier = Modifier.padding(bottom = 16.dp),
                )

                WithdrawalReason.entries.forEach { reason ->
                    BitnagilSelectButton(
                        title = reason.displayText,
                        selected = uiState.selectedReason == reason,
                        onClick = {
                            onReasonSelect(reason)
                            focusManager.clearFocus()
                        },
                        titleTextStyle = BitnagilTheme.typography.body1Medium,
                        colors = BitnagilSelectButtonColor.withdrawal(),
                        modifier = Modifier.padding(bottom = 12.dp),
                    )
                }

                BasicTextField(
                    value = uiState.customReasonText,
                    onValueChange = onCustomReasonChanged,
                    textStyle = BitnagilTheme.typography.subtitle1Medium.copy(
                        color = BitnagilTheme.colors.coolGray10,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = BitnagilTheme.colors.coolGray99,
                            shape = RoundedCornerShape(12.dp),
                        )
                        .height(112.dp)
                        .padding(vertical = 14.dp, horizontal = 20.dp)
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                onReasonSelect(null)
                            }
                        },
                    decorationBox = { innerTextField ->
                        if (uiState.customReasonText.isEmpty()) {
                            Text(
                                text = "기타사항(직접 입력)",
                                color = BitnagilTheme.colors.coolGray80,
                                style = BitnagilTheme.typography.subtitle1Medium,
                            )
                        }
                        innerTextField()
                    },
                )
            }

            Spacer(modifier = Modifier.height(54.dp))
        }

        BitnagilTextButton(
            text = "탈퇴하기",
            onClick = onWithdrawalClick,
            enabled = uiState.isWithdrawalEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (uiState.isTermsChecked) 1f else 0f)
                .padding(vertical = 14.dp, horizontal = 16.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WithdrawalScreenPreview() {
    WithdrawalScreen(
        uiState = WithdrawalState.INIT,
        onTermsToggle = {},
        onReasonSelect = {},
        onCustomReasonChanged = {},
        onBackClick = {},
        onWithdrawalClick = {},
    )
}
