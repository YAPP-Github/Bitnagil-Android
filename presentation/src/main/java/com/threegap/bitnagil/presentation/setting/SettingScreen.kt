package com.threegap.bitnagil.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.block.BitnagilOptionButton
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.presentation.common.flow.collectAsEffect
import com.threegap.bitnagil.presentation.setting.component.atom.settingtitle.SettingTitle
import com.threegap.bitnagil.presentation.setting.component.block.ConfirmDialog
import com.threegap.bitnagil.presentation.setting.model.mvi.SettingSideEffect
import com.threegap.bitnagil.presentation.setting.model.mvi.SettingState

@Composable
fun SettingScreenContainer(
    viewModel: SettingViewModel = hiltViewModel(),
    navigateToBack: () -> Unit,
    navigateToTermsOfService: () -> Unit,
    navigateToPrivacyPolicy: () -> Unit,
    navigateToIntro: () -> Unit,
) {
    val state by viewModel.stateFlow.collectAsState()

    viewModel.sideEffectFlow.collectAsEffect { sideEffect ->
        when (sideEffect) {
            SettingSideEffect.NavigateToIntro -> navigateToIntro()
        }
    }

    state.showConfirmDialog?.let { dialogType ->
        ConfirmDialog(
            type = dialogType,
            onDismiss = viewModel::hideConfirmDialog,
            onConfirm = viewModel::confirmDialogAction,
        )
    }

    SettingScreen(
        state = state,
        toggleServiceAlarm = viewModel::toggleServiceAlarm,
        togglePushAlarm = viewModel::togglePushAlarm,
        onClickUpdate = {},
        onClickBack = navigateToBack,
        onClickTermsOfService = navigateToTermsOfService,
        onClickPrivacyPolicy = navigateToPrivacyPolicy,
        onClickLogout = viewModel::showLogoutDialog,
        onClickWithdrawal = viewModel::showWithdrawalDialog,
    )
}

@Composable
private fun SettingScreen(
    state: SettingState,
    toggleServiceAlarm: () -> Unit,
    togglePushAlarm: () -> Unit,
    onClickUpdate: () -> Unit,
    onClickBack: () -> Unit,
    onClickTermsOfService: () -> Unit,
    onClickPrivacyPolicy: () -> Unit,
    onClickLogout: () -> Unit,
    onClickWithdrawal: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(color = BitnagilTheme.colors.white),
    ) {
        BitnagilTopBar(
            title = "설정",
            showBackButton = true,
            onBackClick = onClickBack,
        )

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState),
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            SettingTitle("정보")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row {
                    Text(
                        text = "버전 ",
                        color = BitnagilTheme.colors.black,
                        style = BitnagilTheme.typography.body1Regular,
                    )
                    Text(
                        text = state.version,
                        color = BitnagilTheme.colors.black,
                        style = BitnagilTheme.typography.body1SemiBold,
                    )
                }
                if (state.version == state.latestVersion) {
                    Text(
                        "최신",
                        modifier = Modifier
                            .background(
                                color = BitnagilTheme.colors.coolGray98,
                                shape = RoundedCornerShape(4.dp),
                            )
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        style = BitnagilTheme.typography.button2.copy(color = BitnagilTheme.colors.coolGray70),
                    )
                } else {
                    Text(
                        "업데이트",
                        modifier = Modifier
                            .background(
                                color = BitnagilTheme.colors.lightBlue200,
                                shape = RoundedCornerShape(4.dp),
                            )
                            .clickable(onClick = onClickUpdate)
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        style = BitnagilTheme.typography.button2.copy(color = BitnagilTheme.colors.navy500),
                    )
                }
            }

            BitnagilOptionButton(
                title = "서비스 이용약관",
                onClick = onClickTermsOfService,
            )

            BitnagilOptionButton(
                title = "개인정보 처리방침",
                onClick = onClickPrivacyPolicy,
            )

            Spacer(modifier = Modifier.height(6.dp))

            HorizontalDivider(modifier = Modifier.height(6.dp), thickness = 6.dp, color = BitnagilTheme.colors.coolGray98)

            Spacer(modifier = Modifier.height(18.dp))

            SettingTitle("계정")

            BitnagilOptionButton(
                title = "로그아웃",
                onClick = onClickLogout,
            )

            BitnagilOptionButton(
                title = "탈퇴하기",
                onClick = onClickWithdrawal,
            )
        }
    }
}

@Composable
@Preview
fun SettingScreenPreview() {
    SettingScreen(
        state = SettingState(
            useServiceAlarm = true,
            usePushAlarm = false,
            version = "1.0.1",
            latestVersion = "1.0.0",
            loading = false,
            showConfirmDialog = null,
        ),
        toggleServiceAlarm = {},
        togglePushAlarm = {},
        onClickUpdate = {},
        onClickBack = {},
        onClickPrivacyPolicy = {},
        onClickTermsOfService = {},
        onClickLogout = {},
        onClickWithdrawal = {},
    )
}
