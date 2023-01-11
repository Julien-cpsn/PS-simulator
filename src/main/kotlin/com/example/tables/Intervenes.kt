package com.example.tables

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime


@Serializable
data class Intervene(
    val id: Int? = 0,
    val firetruck_id: Int,
    val fire_id: Int,
    val start: LocalDateTime,
    val end: LocalDateTime? = null,
    val comment: String? = null
)

object Intervenes: Table() {
    val id = integer("id").autoIncrement()
    val firetruck_id = reference("firetruck_id", Firetrucks.id)
    val fire_id = reference("fire_id", Fires.id)
    val start = datetime("start")
    val end = datetime("end").nullable()
    val comment = varchar("comment", 1024).nullable()

    override val primaryKey = PrimaryKey(id)
}