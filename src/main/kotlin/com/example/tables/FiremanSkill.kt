package com.example.tables

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table


@Serializable
data class FiremanSkill(
    val id: Int? = 0,
    val fireman_id: Int,
    val skill_id: Int
)

object FiremansSkills: Table("firemans_skills"){
    val id = integer("id").autoIncrement()
    val fireman_id = reference("fireman_id", Firemans.id)
    val skill_id = reference("skill_id", Skills.id)

    override val primaryKey = PrimaryKey(id)
}