package com.threegap.bitnagil.presentation.routinelist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.block.BitnagilTopBar
import com.threegap.bitnagil.presentation.home.util.getCurrentWeekDays
import com.threegap.bitnagil.presentation.routinelist.component.template.RoutineDetailsCard
import com.threegap.bitnagil.presentation.routinelist.component.template.WeeklyDatePicker
import java.time.LocalDate

@Composable
fun RoutineListScreenContainer(

) {
    RoutineListScreen()
}

@Composable
private fun RoutineListScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BitnagilTheme.colors.coolGray99)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BitnagilTopBar(
            title = "루틴리스트",
            showBackButton = true,
            onBackClick = {},
        )

        var selectedDate by remember { mutableStateOf<LocalDate>(LocalDate.now()) }

        Spacer(modifier = Modifier.height(4.dp))

        WeeklyDatePicker(
            selectedDate = selectedDate,
            onDateSelect = { selectedDate = it },
            weeklyDates = selectedDate.getCurrentWeekDays(),
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(10) {
                RoutineDetailsCard()
            }
        }
    }
}

@Preview
@Composable
private fun RoutineListScreenPreview() {
    RoutineListScreen()
}
