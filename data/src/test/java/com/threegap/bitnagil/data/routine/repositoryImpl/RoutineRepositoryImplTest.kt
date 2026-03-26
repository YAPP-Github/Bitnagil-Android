package com.threegap.bitnagil.data.routine.repositoryImpl

import com.threegap.bitnagil.data.routine.datasource.RoutineRemoteDataSource
import com.threegap.bitnagil.data.routine.datasourceImpl.RoutineLocalDataSourceImpl
import com.threegap.bitnagil.data.routine.model.request.RoutineCompletionRequest
import com.threegap.bitnagil.data.routine.model.request.RoutineEditRequest
import com.threegap.bitnagil.data.routine.model.request.RoutineRegisterRequest
import com.threegap.bitnagil.data.routine.model.response.RoutineScheduleResponse
import com.threegap.bitnagil.domain.routine.model.DailyRoutines
import com.threegap.bitnagil.domain.routine.model.DayOfWeek
import com.threegap.bitnagil.domain.routine.model.Routine
import com.threegap.bitnagil.domain.routine.model.RoutineCompletionInfo
import com.threegap.bitnagil.domain.routine.model.RoutineSchedule
import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger

@OptIn(ExperimentalCoroutinesApi::class)
class RoutineRepositoryImplTest {

    private lateinit var localDataSource: RoutineLocalDataSourceImpl
    private lateinit var remoteDataSource: FakeRoutineRemoteDataSource
    private lateinit var repository: RoutineRepository

    @Before
    fun setup() {
        localDataSource = RoutineLocalDataSourceImpl()
        remoteDataSource = FakeRoutineRemoteDataSource()
    }

    private fun createRepository(testScheduler: kotlinx.coroutines.test.TestCoroutineScheduler): RoutineRepository {
        val dispatcher = StandardTestDispatcher(testScheduler)
        return RoutineRepositoryImpl(remoteDataSource, localDataSource, dispatcher)
    }

    // --- observeWeeklyRoutines ---

    @Test
    fun `캐시 미스 시 Remote에서 fetch 후 결과를 방출해야 한다`() = runTest {
        repository = createRepository(testScheduler)
        remoteDataSource.scheduleResponse = Result.success(RoutineScheduleResponse(emptyMap()))

        val result = repository.observeWeeklyRoutines("2024-01-01", "2024-01-07").first()

        assertEquals(RoutineSchedule(emptyMap()), result)
        assertEquals(1, remoteDataSource.fetchCount.get())
    }

    @Test
    fun `동일 주차 재구독 시 Remote를 재호출하지 않고 캐시를 반환해야 한다`() = runTest {
        repository = createRepository(testScheduler)
        remoteDataSource.scheduleResponse = Result.success(RoutineScheduleResponse(emptyMap()))
        val startDate = "2024-01-01"
        val endDate = "2024-01-07"

        repository.observeWeeklyRoutines(startDate, endDate).first()
        repository.observeWeeklyRoutines(startDate, endDate).first()

        assertEquals(1, remoteDataSource.fetchCount.get())
    }

    @Test
    fun `다른 주차 구독 시 캐시 초기화 후 Remote를 재호출해야 한다`() = runTest {
        repository = createRepository(testScheduler)
        remoteDataSource.scheduleResponse = Result.success(RoutineScheduleResponse(emptyMap()))

        repository.observeWeeklyRoutines("2024-01-01", "2024-01-07").first()
        repository.observeWeeklyRoutines("2024-01-08", "2024-01-14").first()

        assertEquals(2, remoteDataSource.fetchCount.get())
    }

    // --- applyRoutineToggle ---

    @Test
    fun `토글 호출 시 로컬 캐시에 즉시 optimistic update가 반영되어야 한다`() = runTest {
        repository = createRepository(testScheduler)
        val (dateKey, routineId) = setupCacheWithRoutine(isCompleted = false)

        repository.applyRoutineToggle(
            dateKey = dateKey,
            routineId = routineId,
            completionInfo = RoutineCompletionInfo(routineId, routineCompleteYn = true, subRoutineCompleteYn = emptyList()),
        )

        val updatedRoutine = localDataSource.routineSchedule.value
            ?.dailyRoutines?.get(dateKey)?.routines?.find { it.id == routineId }
        assertTrue(updatedRoutine!!.isCompleted)
    }

    @Test
    fun `토글 후 debounce 경과 시 서버 sync API가 호출되어야 한다`() = runTest {
        repository = createRepository(testScheduler)
        runCurrent() // repositoryScope의 syncTrigger collector 시작
        val (dateKey, routineId) = setupCacheWithRoutine(isCompleted = false)

        repository.applyRoutineToggle(
            dateKey = dateKey,
            routineId = routineId,
            completionInfo = RoutineCompletionInfo(routineId, routineCompleteYn = true, subRoutineCompleteYn = emptyList()),
        )

        assertEquals(0, remoteDataSource.syncCount.get())
        advanceTimeBy(501L)
        assertEquals(1, remoteDataSource.syncCount.get())
    }

    @Test
    fun `A→B→A 토글 시 최종 상태가 원래와 동일하면 API를 호출하지 않아야 한다`() = runTest {
        repository = createRepository(testScheduler)
        runCurrent() // repositoryScope의 syncTrigger collector 시작
        val (dateKey, routineId) = setupCacheWithRoutine(isCompleted = false)
        val completionInfoB = RoutineCompletionInfo(routineId, routineCompleteYn = true, subRoutineCompleteYn = emptyList())
        val completionInfoA = RoutineCompletionInfo(routineId, routineCompleteYn = false, subRoutineCompleteYn = emptyList())

        repository.applyRoutineToggle(dateKey, routineId, completionInfoB)
        repository.applyRoutineToggle(dateKey, routineId, completionInfoA)

        advanceTimeBy(501L)
        assertEquals(0, remoteDataSource.syncCount.get())
    }

    @Test
    fun `sync 실패 시 서버 데이터로 로컬 캐시가 rollback되어야 한다`() = runTest {
        repository = createRepository(testScheduler)
        runCurrent() // repositoryScope의 syncTrigger collector 시작
        remoteDataSource.scheduleResponse = Result.success(RoutineScheduleResponse(emptyMap()))
        remoteDataSource.syncResult = Result.failure(Exception("네트워크 오류"))
        val (dateKey, routineId) = setupCacheWithRoutine(isCompleted = false)

        repository.applyRoutineToggle(
            dateKey = dateKey,
            routineId = routineId,
            completionInfo = RoutineCompletionInfo(routineId, routineCompleteYn = true, subRoutineCompleteYn = emptyList()),
        )
        advanceTimeBy(501L)

        // sync 실패 후 fetchAndSave 호출 → 서버 값(emptyMap)으로 덮어씀
        assertEquals(1, remoteDataSource.fetchCount.get())
        assertEquals(RoutineSchedule(emptyMap()), localDataSource.routineSchedule.value)
    }

    // --- delete / edit / register ---

    @Test
    fun `deleteRoutine 성공 시 캐시 무효화 후 Remote를 재호출해야 한다`() = runTest {
        repository = createRepository(testScheduler)
        remoteDataSource.scheduleResponse = Result.success(RoutineScheduleResponse(emptyMap()))
        localDataSource.saveSchedule(RoutineSchedule(emptyMap()), "2024-01-01", "2024-01-07")

        repository.deleteRoutine("routine1")

        assertEquals(1, remoteDataSource.fetchCount.get())
    }

    @Test
    fun `deleteRoutine 실패 시 Remote를 재호출하지 않아야 한다`() = runTest {
        repository = createRepository(testScheduler)
        remoteDataSource.deleteResult = Result.failure(Exception("삭제 실패"))
        localDataSource.saveSchedule(RoutineSchedule(emptyMap()), "2024-01-01", "2024-01-07")

        repository.deleteRoutine("routine1")

        assertEquals(0, remoteDataSource.fetchCount.get())
    }

    // --- Helpers ---

    private fun setupCacheWithRoutine(isCompleted: Boolean): Pair<String, String> {
        val dateKey = "2024-01-01"
        val routineId = "routine1"
        val schedule = RoutineSchedule(
            dailyRoutines = mapOf(
                dateKey to DailyRoutines(
                    routines = listOf(
                        Routine(
                            id = routineId,
                            name = "테스트 루틴",
                            repeatDays = listOf(DayOfWeek.MONDAY),
                            executionTime = "08:00",
                            startDate = dateKey,
                            endDate = dateKey,
                            routineDate = dateKey,
                            isCompleted = isCompleted,
                            isDeleted = false,
                            subRoutineNames = emptyList(),
                            subRoutineCompletionStates = emptyList(),
                            recommendedRoutineType = null,
                        ),
                    ),
                    isAllCompleted = isCompleted,
                ),
            ),
        )
        localDataSource.saveSchedule(schedule, dateKey, dateKey)
        return dateKey to routineId
    }

    // --- Fake Objects ---

    private class FakeRoutineRemoteDataSource : RoutineRemoteDataSource {
        var scheduleResponse: Result<RoutineScheduleResponse> = Result.success(RoutineScheduleResponse(emptyMap()))
        var syncResult: Result<Unit> = Result.success(Unit)
        var deleteResult: Result<Unit> = Result.success(Unit)
        val fetchCount = AtomicInteger(0)
        val syncCount = AtomicInteger(0)

        override suspend fun fetchWeeklyRoutines(startDate: String, endDate: String): Result<RoutineScheduleResponse> {
            fetchCount.incrementAndGet()
            return scheduleResponse
        }

        override suspend fun syncRoutineCompletion(routineCompletionRequest: RoutineCompletionRequest): Result<Unit> {
            syncCount.incrementAndGet()
            return syncResult
        }

        override suspend fun getRoutine(routineId: String): Result<com.threegap.bitnagil.data.routine.model.response.RoutineResponse> =
            Result.failure(NotImplementedError())

        override suspend fun deleteRoutine(routineId: String): Result<Unit> = deleteResult

        override suspend fun deleteRoutineForDay(routineId: String): Result<Unit> = Result.success(Unit)

        override suspend fun registerRoutine(request: RoutineRegisterRequest): Result<Unit> = Result.success(Unit)

        override suspend fun editRoutine(request: RoutineEditRequest): Result<Unit> = Result.success(Unit)
    }
}
