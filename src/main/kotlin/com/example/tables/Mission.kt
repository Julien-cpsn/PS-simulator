package com.example.tables

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime


@Serializable
data class Mission(
    val id: Int? = 0,
    val start: LocalDateTime,
    val end: LocalDateTime? = null,
    val comment: String? = null
)

object Missions: Table(){
    val id = integer("id").autoIncrement()
    val start = datetime("start")
    val end = datetime("end").nullable()
    val comment = varchar("comment", 1024).nullable()

    override val primaryKey = PrimaryKey(id)
}