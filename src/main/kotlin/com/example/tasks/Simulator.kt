package com.example.tasks

import com.example.utils.database.DatabaseFactory.query
import kotlinx.coroutines.*
import kotlin.random.Random
import com.example.tables.Fire
import com.example.tables.Mission
import com.example.dao.MissionDao
import com.example.dao.FireDao
import java.time.LocalDateTime

object Simulator{
    lateinit var scope: Job

    fun run() = runBlocking {
        scope = launch {
            println("Starting simulator...")
            simulate()
        }
    }

    fun stop() {
        println("Stopping simulator...")
        scope.cancel()
    }

    private suspend fun simulate() {
        while (true) {
            val allFires: List<Fire> = FireDao.getAllFires()
            println("There is actually ${allFires.size} fires")
            for (fire: Fire in allFires) {
                val missionForFire: Mission? = MissionDao.getMission(fire.mission_id)
                if (missionForFire != null && missionForFire.end == null && fire.id != null) {
                    FireDao.updateFire(fire.id, fire.mission_id, fire.skill_id, fire.frequency, fire.intensity + 1, fire.coords)
                    println("Fire at coords ${fire.coords[0]},${fire.coords[1]} has not been stopped yet so its itensity increased to ${fire.intensity + 1}")
                }
            }

            val fireIntensity = Random.nextInt(1, 10)
            val fireLongCoords: Double = Random.nextDouble(45.725000, 45.785000)
            val fireLatCoords: Double = Random.nextDouble(4.815000, 4.900000)

            val mission: Mission = MissionDao.addMission(LocalDateTime.now())
            if (mission.id != null) {
                FireDao.addFire(mission.id, 1, Random.nextFloat(), fireIntensity, arrayOf(fireLongCoords, fireLatCoords))
            }

            println("Generated a fire of intensity $fireIntensity at coords $fireLongCoords, $fireLatCoords")

            val delayBeforeNextFire = Random.nextLong(5, 10)
            println("Waiting $delayBeforeNextFire seconds...")
            delay(delayBeforeNextFire * 10000)
        }
    }
}