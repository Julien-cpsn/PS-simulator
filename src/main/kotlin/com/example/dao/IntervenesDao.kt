package com.example.dao

import com.example.tables.Intervene
import com.example.tables.Intervenes
import com.example.utils.database.DatabaseFactory.query
import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.LocalDateTime

object IntervenesDao {
    private fun rowToIntervene(row: ResultRow) = Intervene (
        id = row[Intervenes.id],
        firetruck_id = row[Intervenes.firetruck_id],
        fire_id = row[Intervenes.fire_id],
        start = row[Intervenes.start].toKotlinLocalDateTime(),
        end = row[Intervenes.end]?.toKotlinLocalDateTime(),
        comment = row[Intervenes.comment]
    )

    suspend fun addIntervene(firetruckId: Int, fireId: Int, start: LocalDateTime, end: LocalDateTime? = null, comment: String? = null): Intervene = query {
        val insertStatement = Intervenes.insert {
            it[Intervenes.firetruck_id] = firetruckId
            it[Intervenes.fire_id] = fireId
            it[Intervenes.start] = start
            it[Intervenes.end] = end
            it[Intervenes.comment] = comment
        }
        rowToIntervene(insertStatement.resultedValues!!.first())
    }

    suspend fun getIntervene(id: Int): Intervene? = query {
        Intervenes
            .select { Intervenes.id eq id }
            .map(::rowToIntervene)
            .singleOrNull()
    }

    suspend fun getAllIntervenes(): List<Intervene> = query {
        Intervenes.selectAll().map(::rowToIntervene)
    }

    suspend fun updateIntervene(id: Int, firetruckId: Int, fireId: Int, start: LocalDateTime, end: LocalDateTime? = null, comment: String? = null): Boolean = query {
        Intervenes.update({ Intervenes.id eq id }) {
            it[Intervenes.firetruck_id] = firetruckId
            it[Intervenes.fire_id] = fireId
            it[Intervenes.start] = start
            it[Intervenes.end] = end
            it[Intervenes.comment] = comment
        } > 0
    }

    suspend fun deleteIntervene(id: Int): Boolean = query {
        Intervenes.deleteWhere { Intervenes.id eq id } > 0
    }

    suspend fun interveneExists(id: Int): Boolean = query {
        Intervenes.select { Intervenes.id eq id }.count() > 0
    }
}