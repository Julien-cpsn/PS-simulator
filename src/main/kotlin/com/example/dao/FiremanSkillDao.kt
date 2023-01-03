package com.example.dao

import com.example.tables.FiremanSkill
import com.example.tables.FiremansSkills
import com.example.utils.database.DatabaseFactory.query
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object FiremanSkillDao {
    private fun rowToFiremanSkill(row: ResultRow) = FiremanSkill (
        id = row[FiremansSkills.id],
        fireman_id = row[FiremansSkills.fireman_id],
        skill_id = row[FiremansSkills.skill_id]
    )

    suspend fun addFiremanSkill(firemanId: Int, skillId: Int): FiremanSkill = query {
        val insertStatement = FiremansSkills.insert {
            it[fireman_id] = firemanId
            it[skill_id] = skillId
        }
        rowToFiremanSkill(insertStatement.resultedValues!!.first())
    }

    suspend fun getFiremanSkill(id: Int): FiremanSkill? = query {
        FiremansSkills
            .select { FiremansSkills.id eq id }
            .map(::rowToFiremanSkill)
            .singleOrNull()
    }

    suspend fun getAllFiremansSkills(): List<FiremanSkill> = query {
        FiremansSkills.selectAll().map(::rowToFiremanSkill)
    }

    suspend fun updateFiremanSkill(id: Int, firemanId: Int, skillId: Int): Boolean = query {
        FiremansSkills.update({ FiremansSkills.id eq id }) {
            it[fireman_id] = firemanId
            it[skill_id] = skillId
        } > 0
    }

    suspend fun deleteFiremanSkill(id: Int): Boolean = query {
        FiremansSkills.deleteWhere { FiremansSkills.id eq id } > 0
    }

    suspend fun firemanSkillExists(id: Int): Boolean = query {
        FiremansSkills.select { FiremansSkills.id eq id }.count() > 0
    }
}