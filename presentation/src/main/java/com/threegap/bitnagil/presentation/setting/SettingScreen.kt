package com.threegap.bitnagil.presentation.setting

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.block.BitnagilAlertDialog
import com.threegap.bitnagil.designsystem.component.block.BitnagilOptionButton
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.designsystem.modifier.clickableWithoutRipple
import com.threegap.bitnagil.presentation.common.playstore.UpdateAvailableState
import com.threegap.bitnagil.presentation.common.playstore.openAppInPlayStore
import com.threegap.bitnagil.presentation.common.playstore.updateAvailable
import com.threegap.bitnagil.presentation.setting.component.atom.settingtitle.SettingTitle
import com.threegap.bitnagil.presentation.setting.model.mvi.SettingSideEffect
import com.threegap.bitnagil.presentation.setting.model.mvi.SettingState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SettingScreenContainer(
    viewModel: SettingViewModel = hiltViewModel(),
    navigateToBack: () -> Unit,
    navigateToTermsOfService: () -> Unit,
    navigateToPrivacyPolicy: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToWithdrawal: () -> Unit,
) {
    val context = LocalContext.current
    val activity = context as? ComponentActivity
    val state by viewModel.collectAsState()
    val updateAvailableState = updateAvailable()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            SettingSideEffect.NavigateToLogin -> navigateToLogin()
            SettingSideEffect.NavigateToWithdrawal -> navigateToWithdrawal()
        }
    }

    if (state.logoutConfirmDialogVisible) {
        BitnagilAlertDialog(
            title = "로그아웃 할까요?",
            description = "이 기기에서 계정이 로그아웃되고, 다시\n로그인해야 서비스를 계속 이용할 수 있어요.",
            dismissButtonText = "취소",
            confirmButtonText = "로그아웃",
            onDismiss = viewModel::hideConfirmDialog,
            onConfirm = viewModel::logout,
        )
    }

    SettingScreen(
        state = state,
        updateAvailableState = updateAvailableState,
        toggleServiceAlarm = viewModel::toggleServiceAlarm,
        togglePushAlarm = viewModel::togglePushAlarm,
        onClickUpdate = { openAppInPlayStore(activity = activity, shouldFinishApp = false) },
        onClickBack = navigateToBack,
        onClickTermsOfService = navigateToTermsOfService,
        onClickPrivacyPolicy = navigateToPrivacyPolicy,
        onClickLogout = viewModel::showLogoutDialog,
        onClickWithdrawal = viewModel::navigateToWithdrawal,
    )
}

@Composable
private fun SettingScreen(
    state: SettingState,
    updateAvailableState: UpdateAvailableState,
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
            .background(color = BitnagilTheme.colors.white)
            .statusBarsPadding(),
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
                        style = BitnagilTheme.typography.body1Medium,
                    )
                    Text(
                        text = state.version,
                        color = BitnagilTheme.colors.black,
                        style = BitnagilTheme.typography.body1SemiBold,
                    )
                }

                when (updateAvailableState) {
                    UpdateAvailableState.Latest -> Text(
                        text = "최신",
                        color = BitnagilTheme.colors.coolGray70,
                        style = BitnagilTheme.typography.button2,
                        modifier = Modifier
                            .background(
                                color = BitnagilTheme.colors.coolGray98,
                                shape = RoundedCornerShape(8.dp),
                            )
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                    )
                    UpdateAvailableState.NEED_UPDATE -> Text(
                        text = "업데이트",
                        color = BitnagilTheme.colors.orange500,
                        style = BitnagilTheme.typography.button2,
                        modifier = Modifier
                            .background(
                                color = BitnagilTheme.colors.orange50,
                                shape = RoundedCornerShape(8.dp),
                            )
                            .clickableWithoutRipple(onClick = onClickUpdate)
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                    )
                    UpdateAvailableState.NONE -> {}
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
            loading = false,
            logoutConfirmDialogVisible = false,
        ),
        updateAvailableState = UpdateAvailableState.Latest,
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
