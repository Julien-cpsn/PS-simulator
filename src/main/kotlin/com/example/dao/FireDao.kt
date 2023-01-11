package com.example.dao

import com.example.tables.Fire
import com.example.tables.Fires
import com.example.utils.database.DatabaseFactory.query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object FireDao {
    private fun rowToFire(row: ResultRow) = Fire (
        id = row[Fires.id],
        mission_id = row[Fires.mission_id],
        skill_id = row[Fires.skill_id],
        frequency = row[Fires.frequency],
        intensity = row[Fires.intensity],
        coords = row[Fires.coords]
    )

    suspend fun addFire(missionId: Int, skillId: Int, frequency: Float, intensity: Int, coords: Array<Double>): Fire = query {
        val insertStatement = Fires.insert {
            it[Fires.mission_id] = missionId
            it[Fires.skill_id] = skillId
            it[Fires.frequency] = frequency
            it[Fires.intensity] = intensity
            it[Fires.coords] = coords
        }
        rowToFire(insertStatement.resultedValues!!.first())
    }

    suspend fun getFire(id: Int): Fire? = query {
        Fires
            .select { Fires.id eq id }
            .map(::rowToFire)
            .singleOrNull()
    }

    suspend fun getAllFires(): List<Fire> = query {
        Fires.selectAll().map(::rowToFire)
    }

    suspend fun updateFire(id: Int, missionId: Int, skillId: Int, frequency: Float, intensity: Int, coords: Array<Double>): Boolean = query {
        Fires.update({ Fires.id eq id }) {
            it[Fires.mission_id] = missionId
            it[Fires.skill_id] = skillId
            it[Fires.frequency] = frequency
            it[Fires.intensity] = intensity
            it[Fires.coords] = coords
        } > 0
    }

    suspend fun deleteFire(id: Int): Boolean = query {
        Fires.deleteWhere { Fires.id eq id } > 0
    }

    suspend fun fireExists(id: Int): Boolean = query {
        Fires.select { Fires.id eq id }.count() > 0
    }
}