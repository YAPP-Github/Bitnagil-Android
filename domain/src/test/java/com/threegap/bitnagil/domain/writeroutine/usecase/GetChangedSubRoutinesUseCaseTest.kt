package com.threegap.bitnagil.domain.writeroutine.usecase

import com.threegap.bitnagil.domain.writeroutine.model.SubRoutine
import com.threegap.bitnagil.domain.writeroutine.model.SubRoutineDiff
import org.junit.Assert.assertEquals
import org.junit.Test

class GetChangedSubRoutinesUseCaseTest {
    private val getChangedSubRoutinesUseCase = GetChangedSubRoutinesUseCase()

    @Test
    fun `세부 루틴을 변경하지 않은 경우 빈 문자열이 리턴되어야 한다`() {
        val oldSubRoutines = listOf(
            SubRoutine(id = "1", name = "AAA", sort = 1),
            SubRoutine(id = "2", name = "BBB", sort = 2),
        )

        val newSubRoutineNames = listOf("AAA", "BBB")

        val changedSubRoutines = getChangedSubRoutinesUseCase(oldSubRoutines, newSubRoutineNames)

        assertEquals(emptyList<SubRoutineDiff>(), changedSubRoutines)
    }

    @Test
    fun `세부 루틴의 순서가 변경된 경우 변경된 순서를 반영해야 한다`() {
        val oldSubRoutines = listOf(
            SubRoutine(id = "1", name = "AAA", sort = 1),
            SubRoutine(id = "2", name = "BBB", sort = 2),
            SubRoutine(id = "3", name = "CCC", sort = 3),
        )

        val newSubRoutineNames = listOf("BBB", "CCC", "AAA")

        val changedSubRoutines = getChangedSubRoutinesUseCase(oldSubRoutines, newSubRoutineNames)

        val answer = listOf(
            SubRoutineDiff(id = "2", name = "BBB", sort = 1),
            SubRoutineDiff(id = "3", name = "CCC", sort = 2),
            SubRoutineDiff(id = "1", name = "AAA", sort = 3),
        )

        assertEquals(answer, changedSubRoutines)
    }

    @Test
    fun `새로운 세부 루틴이 추가될 경우, 추가된 세부 루틴에 대해 루틴 이름과 순서를 지정해야 한다`() {
        val oldSubRoutines = listOf(
            SubRoutine(id = "1", name = "AAA", sort = 1),
            SubRoutine(id = "2", name = "BBB", sort = 2),
        )

        val newSubRoutineNames = listOf("AAA", "BBB", "CCC")

        val changedSubRoutines = getChangedSubRoutinesUseCase(oldSubRoutines, newSubRoutineNames)

        val answer = listOf(
            SubRoutineDiff(id = null, name = "CCC", sort = 3),
        )

        assertEquals(answer, changedSubRoutines)
    }

    @Test
    fun `세부 루틴을 제거하는 경우, 제거한 세부 루틴의 id를 제외한 나머지를 null로 지정해야 한다`() {
        val oldSubRoutines = listOf(
            SubRoutine(id = "1", name = "AAA", sort = 1),
            SubRoutine(id = "2", name = "BBB", sort = 2),
        )

        val newSubRoutineNames = listOf("AAA")

        val changedSubRoutines = getChangedSubRoutinesUseCase(oldSubRoutines, newSubRoutineNames)

        val answer = listOf(
            SubRoutineDiff(id = "2", name = null, sort = null),
        )

        assertEquals(answer, changedSubRoutines)
    }

    @Test
    fun `세부 루틴 제거로 인해 기존 세부 루틴의 순서가 변경된 경우, 해당 세부 루틴의 순서를 변경해야 한다`() {
        val oldSubRoutines = listOf(
            SubRoutine(id = "1", name = "AAA", sort = 1),
            SubRoutine(id = "2", name = "BBB", sort = 2),
        )

        val newSubRoutineNames = listOf("BBB")

        val changedSubRoutines = getChangedSubRoutinesUseCase(oldSubRoutines, newSubRoutineNames)

        val answer = listOf(
            SubRoutineDiff(id = "1", name = null, sort = null),
            SubRoutineDiff(id = "2", name = "BBB", sort = 1),
        )

        assertEquals(answer, changedSubRoutines)
    }

    @Test
    fun `심화 케이스 - 세부 루틴의 전체 내용이 제거되고 새롭게 추가된 경우`() {
        val oldSubRoutines = listOf(
            SubRoutine(id = "1", name = "AAA", sort = 1),
            SubRoutine(id = "2", name = "BBB", sort = 2),
            SubRoutine(id = "3", name = "CCC", sort = 3),
        )

        val newSubRoutineNames = listOf("DDD", "EEE", "FFF")

        val changedSubRoutines = getChangedSubRoutinesUseCase(oldSubRoutines, newSubRoutineNames)

        val answer = listOf(
            SubRoutineDiff(id = "1", name = null, sort = null),
            SubRoutineDiff(id = "2", name = null, sort = null),
            SubRoutineDiff(id = "3", name = null, sort = null),
            SubRoutineDiff(id = null, name = "DDD", sort = 1),
            SubRoutineDiff(id = null, name = "EEE", sort = 2),
            SubRoutineDiff(id = null, name = "FFF", sort = 3),
        )

        assertEquals(answer, changedSubRoutines)
    }

    @Test
    fun `심화 케이스 - 동일한 세부 루틴명을 가지는 경우`() {
        val oldSubRoutines = listOf(
            SubRoutine(id = "1", name = "AAA", sort = 1),
            SubRoutine(id = "2", name = "BBB", sort = 2),
            SubRoutine(id = "3", name = "AAA", sort = 3),
            SubRoutine(id = "4", name = "AAA", sort = 4),
        )

        val newSubRoutineNames = listOf("AAA", "AAA")

        val changedSubRoutines = getChangedSubRoutinesUseCase(oldSubRoutines, newSubRoutineNames)

        val answer = listOf(
            SubRoutineDiff(id = "2", name = null, sort = null),
            SubRoutineDiff(id = "4", name = null, sort = null),
            SubRoutineDiff(id = "3", name = "AAA", sort = 2),
        )

        assertEquals(answer, changedSubRoutines)
    }
}
