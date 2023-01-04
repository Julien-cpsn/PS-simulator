package com.example.dao

import com.example.tables.Mission
import com.example.tables.Missions
import com.example.utils.database.DatabaseFactory.query
import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.LocalDateTime

object MissionDao {
    private fun rowToMission(row: ResultRow) = Mission (
        id = row[Missions.id],
        start = row[Missions.start].toKotlinLocalDateTime(),
        end = row[Missions.end]?.toKotlinLocalDateTime(),
        comment = row[Missions.comment]
    )

    suspend fun addMission(start: LocalDateTime, end: LocalDateTime? = null, comment: String? = null): Mission = query {
        val insertStatement = Missions.insert {
            it[Missions.start] = start
            it[Missions.end] = end
            it[Missions.comment] = comment
        }
        rowToMission(insertStatement.resultedValues!!.first())
    }

    suspend fun getMission(id: Int): Mission? = query {
        Missions
            .select { Missions.id eq id }
            .map(::rowToMission)
            .singleOrNull()
    }

    suspend fun getAllMissions(): List<Mission> = query {
        Missions.selectAll().map(::rowToMission)
    }

    suspend fun updateMission(id: Int, start: LocalDateTime, end: LocalDateTime? = null, comment: String? = null): Boolean = query {
        Missions.update({ Missions.id eq id }) {
            it[Missions.start] = start
            it[Missions.end] = end
            it[Missions.comment] = comment
        } > 0
    }

    suspend fun deleteMission(id: Int): Boolean = query {
        Missions.deleteWhere { Missions.id eq id } > 0
    }

    suspend fun missionsExists(id: Int): Boolean = query {
        Missions.select { Missions.id eq id }.count() > 0
    }
}