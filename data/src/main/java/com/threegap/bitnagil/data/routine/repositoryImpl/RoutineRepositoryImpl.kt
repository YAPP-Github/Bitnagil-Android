package com.threegap.bitnagil.data.routine.repositoryImpl

import com.threegap.bitnagil.data.di.IoDispatcher
import com.threegap.bitnagil.data.routine.datasource.RoutineLocalDataSource
import com.threegap.bitnagil.data.routine.datasource.RoutineRemoteDataSource
import com.threegap.bitnagil.data.routine.model.request.toDto
import com.threegap.bitnagil.data.routine.model.response.toDomain
import com.threegap.bitnagil.domain.routine.model.Routine
import com.threegap.bitnagil.domain.routine.model.RoutineCompletionInfo
import com.threegap.bitnagil.domain.routine.model.RoutineCompletionInfos
import com.threegap.bitnagil.domain.routine.model.RoutineEditInfo
import com.threegap.bitnagil.domain.routine.model.RoutineRegisterInfo
import com.threegap.bitnagil.domain.routine.model.RoutineSchedule
import com.threegap.bitnagil.domain.routine.repository.RoutineRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoutineRepositoryImpl @Inject constructor(
    private val routineRemoteDataSource: RoutineRemoteDataSource,
    private val routineLocalDataSource: RoutineLocalDataSource,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher,
) : RoutineRepository {

    private val repositoryScope = CoroutineScope(SupervisorJob() + dispatcher)
    private val pendingChangesByDate = mutableMapOf<String, MutableMap<String, RoutineCompletionInfo>>()
    private val originalStatesByDate = mutableMapOf<String, MutableMap<String, RoutineCompletionInfo>>()
    private val syncTrigger = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    init {
        @OptIn(FlowPreview::class)
        repositoryScope.launch {
            syncTrigger
                .debounce(500L)
                .collect { dateKey -> flushPendingChanges(dateKey) }
        }
    }

    override fun observeWeeklyRoutines(startDate: String, endDate: String): Flow<RoutineSchedule> = flow {
        if (routineLocalDataSource.lastFetchRange != (startDate to endDate)) {
            routineLocalDataSource.clearCache()
            fetchAndSave(startDate, endDate)
        }
        emitAll(
            routineLocalDataSource.routineSchedule
                .onEach { if (it == null) fetchAndSave(startDate, endDate) }
                .filterNotNull(),
        )
    }

    private suspend fun fetchAndSave(startDate: String, endDate: String) {
        routineRemoteDataSource.fetchWeeklyRoutines(startDate, endDate)
            .onSuccess { routineLocalDataSource.saveSchedule(it.toDomain(), startDate, endDate) }
            .onFailure { throw it }
    }

    override suspend fun applyRoutineToggle(dateKey: String, routineId: String, completionInfo: RoutineCompletionInfo) {
        if (originalStatesByDate[dateKey]?.containsKey(routineId) != true) {
            routineLocalDataSource.getCompletionInfo(dateKey, routineId)?.let {
                originalStatesByDate.getOrPut(dateKey) { mutableMapOf() }[routineId] = it
            }
        }
        routineLocalDataSource.applyOptimisticToggle(dateKey, routineId, completionInfo)
        pendingChangesByDate.getOrPut(dateKey) { mutableMapOf() }[routineId] = completionInfo
        syncTrigger.emit(dateKey)
    }

    private suspend fun flushPendingChanges(dateKey: String) {
        val snapshot = pendingChangesByDate.remove(dateKey)
        val originals = originalStatesByDate.remove(dateKey)
        val actualChanges = snapshot?.filter { (routineId, pending) -> originals?.get(routineId) != pending }
        if (actualChanges.isNullOrEmpty()) return

        val syncRequest = RoutineCompletionInfos(routineCompletionInfos = actualChanges.values.toList())
        routineRemoteDataSource.syncRoutineCompletion(syncRequest.toDto())
            .onFailure {
                val range = routineLocalDataSource.lastFetchRange ?: return
                fetchAndSave(range.first, range.second)
            }
    }

    private suspend fun refreshCache() {
        val range = routineLocalDataSource.lastFetchRange ?: return
        fetchAndSave(range.first, range.second)
    }

    override suspend fun getRoutine(routineId: String): Result<Routine> =
        routineRemoteDataSource.getRoutine(routineId).map { it.toDomain() }

    override suspend fun deleteRoutine(routineId: String): Result<Unit> =
        routineRemoteDataSource.deleteRoutine(routineId).also {
            if (it.isSuccess) refreshCache()
        }

    override suspend fun deleteRoutineForDay(routineId: String): Result<Unit> =
        routineRemoteDataSource.deleteRoutineForDay(routineId).also {
            if (it.isSuccess) refreshCache()
        }

    override suspend fun registerRoutine(routineRegisterInfo: RoutineRegisterInfo): Result<Unit> =
        routineRemoteDataSource.registerRoutine(routineRegisterInfo.toDto()).also {
            if (it.isSuccess) refreshCache()
        }

    override suspend fun editRoutine(routineEditInfo: RoutineEditInfo): Result<Unit> =
        routineRemoteDataSource.editRoutine(routineEditInfo.toDto()).also {
            if (it.isSuccess) refreshCache()
        }
}
