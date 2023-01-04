package com.example.tasks

import com.example.dao.*
import com.example.utils.database.DatabaseFactory.query
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

object SeedDatabase {
    fun run() = runBlocking {
        launch {
            seed()
        }
    }

    private suspend fun seed() = query {
        FirestationDao.addFirestation("Caserne Lyon-Confluence", arrayOf(45.7468368,4.825788))
        FirestationDao.addFirestation("Caserne Villeurbanne", arrayOf(45.7790285, 4.878109))

        FiretruckDao.addFiretruck(1, arrayOf(45.7468368,4.825788))
        FiretruckDao.addFiretruck(2, arrayOf(45.7790285, 4.878109))

        FiremanDao.addFireman(1, 1, "Roger", "VITESSE")
        FiremanDao.addFireman(2, 2, "Alexis", "THOMAS")

        SkillDao.addSkill("CMIC")
        SkillDao.addSkill("GRIMP")

        FiremanSkillDao.addFiremanSkill(1, 1)
        FiremanSkillDao.addFiremanSkill(2, 1)
        FiremanSkillDao.addFiremanSkill(2, 2)

        MissionDao.addMission(LocalDateTime.of(2023, 1, 3, 11, 33))
        MissionDao.addMission(LocalDateTime.of(2023, 1, 5, 18, 0), LocalDateTime.of(2023, 1, 7, 6, 30), "Mission test")
    }
}