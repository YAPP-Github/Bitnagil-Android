package com.threegap.bitnagil.data.writeroutine.datasourceImpl

import com.threegap.bitnagil.data.common.safeUnitApiCall
import com.threegap.bitnagil.data.writeroutine.datasource.WriteRoutineDataSource
import com.threegap.bitnagil.data.writeroutine.model.request.EditRoutineRequest
import com.threegap.bitnagil.data.writeroutine.model.request.RegisterRoutineRequest
import com.threegap.bitnagil.data.writeroutine.service.WriteRoutineService
import javax.inject.Inject

class WriteRoutineDataSourceImpl @Inject constructor(
    private val writeRoutineService: WriteRoutineService,
) : WriteRoutineDataSource {
    override suspend fun registerRoutine(request: RegisterRoutineRequest): Result<Unit> {
        return safeUnitApiCall {
            writeRoutineService.postRoutine(request)
        }
    }

    override suspend fun editRoutine(request: EditRoutineRequest): Result<Unit> {
        return safeUnitApiCall {
            writeRoutineService.patchRoutine(request)
        }
    }
}
