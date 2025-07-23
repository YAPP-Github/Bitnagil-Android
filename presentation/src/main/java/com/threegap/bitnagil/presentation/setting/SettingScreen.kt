package com.threegap.bitnagil.presentation.setting

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
import com.threegap.bitnagil.presentation.setting.component.atom.settingtitle.SettingTitle
import com.threegap.bitnagil.presentation.setting.component.atom.toggleswitch.ToggleSwitch
import com.threegap.bitnagil.presentation.setting.component.block.settingrowbutton.SettingRowButton
import com.threegap.bitnagil.presentation.setting.model.mvi.SettingState

@Composable
fun SettingScreenContainer(
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val state by viewModel.stateFlow.collectAsState()

    SettingScreen(
        state = state,
        toggleServiceAlarm = viewModel::toggleServiceAlarm,
        togglePushAlarm = viewModel::togglePushAlarm,
        onClickUpdate = {},
    )
}

@Composable
private fun SettingScreen(
    state: SettingState,
    toggleServiceAlarm: () -> Unit,
    togglePushAlarm: () -> Unit,
    onClickUpdate: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BitnagilTheme.colors.white),
    ) {
        Box(
            modifier = Modifier
                .height(54.dp)
                .fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(36.dp)
                    .background(BitnagilTheme.colors.black)
                    .align(Alignment.CenterStart)
                    .clickable {
                    },
            )

            Text(
                "설정",
                modifier = Modifier.align(Alignment.Center),
                style = BitnagilTheme.typography.title3SemiBold,
            )
        }

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState),
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            SettingTitle("알림")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("서비스 이용 알림", style = BitnagilTheme.typography.body1Regular)
                ToggleSwitch(checked = state.useServiceAlarm, onCheckedChange = { toggleServiceAlarm() })
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("푸시알림", style = BitnagilTheme.typography.body1Regular)
                ToggleSwitch(checked = state.usePushAlarm, onCheckedChange = { togglePushAlarm() })
            }

            Spacer(modifier = Modifier.height(6.dp))

            HorizontalDivider(modifier = Modifier.height(6.dp), thickness = 6.dp, color = BitnagilTheme.colors.coolGray98)

            Spacer(modifier = Modifier.height(18.dp))

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
                    Text("버전 ", style = BitnagilTheme.typography.body1Regular)
                    Text(state.version, style = BitnagilTheme.typography.body1SemiBold)
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

            SettingRowButton(text = "서비스 이용약관", onClick = {})

            SettingRowButton(text = "개인정보 처리방침", onClick = {})

            Spacer(modifier = Modifier.height(6.dp))

            HorizontalDivider(modifier = Modifier.height(6.dp), thickness = 6.dp, color = BitnagilTheme.colors.coolGray98)

            Spacer(modifier = Modifier.height(18.dp))

            SettingTitle("계정")

            SettingRowButton(text = "로그아웃", onClick = {})

            SettingRowButton(text = "탈퇴하기", onClick = {})
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
        ),
        toggleServiceAlarm = {},
        togglePushAlarm = {},
        onClickUpdate = {},
    )
}
