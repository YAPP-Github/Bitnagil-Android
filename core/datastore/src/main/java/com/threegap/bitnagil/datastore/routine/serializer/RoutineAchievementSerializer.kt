package com.threegap.bitnagil.datastore.routine.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.threegap.bitnagil.datastore.routine.model.RoutineAchievement
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object RoutineAchievementSerializer : Serializer<RoutineAchievement> {
    override val defaultValue: RoutineAchievement = RoutineAchievement()

    override suspend fun readFrom(input: InputStream): RoutineAchievement =
        try {
            Json.decodeFromString(input.readBytes().decodeToString())
        } catch (e: SerializationException) {
            throw CorruptionException("루틴 달성 플래그 역직렬화에 실패했습니다.", e)
        }

    override suspend fun writeTo(t: RoutineAchievement, output: OutputStream) {
        output.write(Json.encodeToString(t).toByteArray())
    }
}
