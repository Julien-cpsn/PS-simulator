package com.example.tables

import com.example.utils.database.point
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table


@Serializable
data class Firetruck(
    val id: Int? = 0,
    val firestation_id: Int,
    val coords: Array<Double>
)

object Firetrucks: Table(){
    val id = integer("id").autoIncrement()
    val firestation_id = reference("firestation_id", Firestations.id)
    val coords = point("coords")

    override val primaryKey = PrimaryKey(id)
}