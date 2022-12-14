package com.example.dao

import com.example.tables.Firestations
import com.example.tables.Firetruck
import com.example.tables.Firetrucks
import com.example.utils.database.DatabaseFactory.query
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object FiretruckDao {
    private fun rowToFiretruck(row: ResultRow) = Firetruck (
        id = row[Firetrucks.id],
        firestation_id = row[Firetrucks.firestation_id],
        coords = row[Firetrucks.coords]
    )

    suspend fun addFiretruck(firestationId: Int, coords: Array<Double>): Firetruck = query {
        val insertStatement = Firetrucks.insert {
            it[Firetrucks.firestation_id] = firestationId
            it[Firetrucks.coords] = coords
        }
        rowToFiretruck(insertStatement.resultedValues!!.first())
    }

    suspend fun getFiretruck(id: Int): Firetruck? = query {
        Firetrucks
            .select { Firetrucks.id eq id }
            .map(::rowToFiretruck)
            .singleOrNull()
    }

    suspend fun getAllFiretrucks(): List<Firetruck> = query {
        Firetrucks.selectAll().map(::rowToFiretruck)
    }

    suspend fun updateFiretruck(id: Int, firestationId: Int, coords: Array<Double>): Boolean = query {
        Firetrucks.update({ Firetrucks.id eq id }) {
            it[Firetrucks.firestation_id] = firestationId
            it[Firetrucks.coords] = coords
        } > 0
    }

    suspend fun deleteFiretruck(id: Int): Boolean = query {
        Firetrucks.deleteWhere { Firetrucks.id eq id } > 0
    }

    suspend fun firetruckExists(id: Int): Boolean = query {
        Firetrucks.select { Firetrucks.id eq id }.count() > 0
    }
}