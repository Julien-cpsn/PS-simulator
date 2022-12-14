package com.example.dao

import com.example.tables.Fireman
import com.example.tables.Firemans
import com.example.utils.database.DatabaseFactory.query
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object FiremanDao {
    private fun rowToFireman(row: ResultRow) = Fireman (
        id = row[Firemans.id],
        firestation_id = row[Firemans.firestation_id],
        firetruck_id = row[Firemans.firetruck_id],
        first_name = row[Firemans.first_name],
        last_name = row[Firemans.last_name]
    )

    suspend fun addFireman(firestationId: Int, firetruckId: Int, firstName: String, lastName: String): Fireman = query {
        val insertStatement = Firemans.insert {
            it[firestation_id] = firestationId
            it[firetruck_id] = firetruckId
            it[first_name] = firstName
            it[last_name] = lastName
        }
        rowToFireman(insertStatement.resultedValues!!.first())
    }

    suspend fun getFireman(id: Int): Fireman? = query {
        Firemans
            .select { Firemans.id eq id }
            .map(::rowToFireman)
            .singleOrNull()
    }

    suspend fun getAllFiremans(): List<Fireman> = query {
        Firemans.selectAll().map(::rowToFireman)
    }

    suspend fun updateFireman(id: Int, firestationId: Int, firetruckId: Int, firstName: String, lastName: String): Boolean = query {
        Firemans.update({ Firemans.id eq id }) {
            it[firestation_id] = firestationId
            it[firetruck_id] = firetruckId
            it[first_name] = firstName
            it[last_name] = lastName
        } > 0
    }

    suspend fun deleteFireman(id: Int): Boolean = query {
        Firemans.deleteWhere { Firemans.id eq id } > 0
    }

    suspend fun firemanExists(id: Int): Boolean = query {
        Firemans.select { Firemans.id eq id }.count() > 0
    }
}