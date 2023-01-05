package com.example.tables

import com.example.utils.database.point
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table


@Serializable
data class Fire(
    val id: Int? = 0,
    val mission_id: Int,
    val frequency: Float,
    val intensity: Int,
    val coords: Array<Double>
)

object Fires: Table(){
    val id = integer("id").autoIncrement()
    val mission_id = reference("mission_id", Missions.id)
    val frequency = float("frequency")
    val intensity = integer("intensity")
    val coords = point("coords")

    override val primaryKey = PrimaryKey(id)
}