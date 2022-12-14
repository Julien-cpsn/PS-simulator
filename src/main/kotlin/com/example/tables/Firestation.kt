package com.example.tables

import com.example.utils.database.point
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table


@Serializable
data class Firestation(
    val id: Int? = 0,
    val name: String,
    val coords: Array<Double>
)

object Firestations: Table(){
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128).uniqueIndex()
    val coords = point("coords")

    override val primaryKey = PrimaryKey(id)
}