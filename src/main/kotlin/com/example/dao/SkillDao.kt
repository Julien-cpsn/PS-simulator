package com.example.dao

import com.example.tables.Skill
import com.example.tables.Skills
import com.example.utils.database.DatabaseFactory.query
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object SkillDao {
    private fun rowToSkill(row: ResultRow) = Skill (
        id = row[Skills.id],
        name = row[Skills.name],
    )

    suspend fun addSkill(name: String): Skill = query {
        val insertStatement = Skills.insert {
            it[Skills.name] = name
        }
        rowToSkill(insertStatement.resultedValues!!.first())
    }

    suspend fun getSkill(id: Int): Skill? = query {
        Skills
            .select { Skills.id eq id }
            .map(::rowToSkill)
            .singleOrNull()
    }

    suspend fun getAllSkills(): List<Skill> = query {
        Skills.selectAll().map(::rowToSkill)
    }

    suspend fun updateSkill(id: Int, name: String): Boolean = query {
        Skills.update({ Skills.id eq id }) {
            it[Skills.name] = name
        } > 0
    }

    suspend fun deleteSkill(id: Int): Boolean = query {
        Skills.deleteWhere { Skills.id eq id } > 0
    }

    suspend fun skillExists(id: Int): Boolean = query {
        Skills.select { Skills.id eq id }.count() > 0
    }
}