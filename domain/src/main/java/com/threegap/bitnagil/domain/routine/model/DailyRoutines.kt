package com.threegap.bitnagil.domain.routine.model

/**
 * 특정 하루("일간")의 루틴 정보.
 * [RoutineSchedule] Map의 '값(value)'으로 사용됩니다.
 *
 * @property routines 해당 날짜에 포함된 [Routine](개별 루틴)의 목록.
 * @property isAllCompleted 해당 날짜의 모든 루틴이 완료되었는지 여부.
 */
data class DailyRoutines(
    val routines: List<Routine>,
    val isAllCompleted: Boolean,
)
