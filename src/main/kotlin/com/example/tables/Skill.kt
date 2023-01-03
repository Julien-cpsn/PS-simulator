package com.example.tables

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table


@Serializable
data class Skill(
    val id: Int? = 0,
    val name: String
)

object Skills: Table(){
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)

    override val primaryKey = PrimaryKey(id)
}