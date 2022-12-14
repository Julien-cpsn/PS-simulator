package com.example.dao

import com.example.tables.Firestation
import com.example.tables.Firestations
import com.example.utils.database.DatabaseFactory.query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object FirestationDao {
    private fun rowToFirestation(row: ResultRow) = Firestation (
        id = row[Firestations.id],
        name = row[Firestations.name],
        coords = row[Firestations.coords]
    )

    suspend fun addFirestation(name: String, coords: Array<Double>): Firestation = query {
        val insertStatement = Firestations.insert {
            it[Firestations.name] = name
            it[Firestations.coords] = coords
        }
        rowToFirestation(insertStatement.resultedValues!!.first())
    }

    suspend fun getFirestation(id: Int): Firestation? = query {
        Firestations
            .select { Firestations.id eq id }
            .map(::rowToFirestation)
            .singleOrNull()
    }

    suspend fun getAllFirestations(): List<Firestation> = query {
        Firestations.selectAll().map(::rowToFirestation)
    }

    suspend fun updateFirestation(id: Int, name: String, coords: Array<Double>): Boolean = query {
        Firestations.update({ Firestations.id eq id }) {
            it[Firestations.name] = name
            it[Firestations.coords] = coords
        } > 0
    }

    suspend fun deleteFirestation(id: Int): Boolean = query {
        Firestations.deleteWhere { Firestations.id eq id } > 0
    }

    suspend fun firestationExists(id: Int): Boolean = query {
        Firestations.select { Firestations.id eq id }.count() > 0
    }
}