package com.example.tasks

import com.example.utils.mqtt.AtMostOnce
import com.example.utils.mqtt.Mqtt
import com.example.utils.mqtt.Topic
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object EmergencyManager {
    lateinit var scope: Job

    fun run() = runBlocking {
        scope = launch {
            println("Starting emergency manager...")
            manage()
        }
    }

    fun stop() {
        println("Stopping emergency manager...")
        scope.cancel()
    }

    private suspend fun manage() {
        val microbit = Topic("microbit")
        Mqtt.client.publishMessageTo(microbit, "TESTTT", AtMostOnce, false)
    }
}