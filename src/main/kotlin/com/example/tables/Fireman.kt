package com.example.tables

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table


@Serializable
data class Fireman(
    val id: Int? = 0,
    val firestation_id: Int,
    val firetruck_id: Int,
    val last_name: String,
    val first_name: String
)

object Firemans: Table(){
    val id = integer("id").autoIncrement()
    val firestation_id = reference("firestation_id", Firestations.id)
    val firetruck_id = reference("firetruck_id", Firetrucks.id)
    val first_name = varchar("first_name", 128)
    val last_name = varchar("last_name", 128)

    override val primaryKey = PrimaryKey(id)
}