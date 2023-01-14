package com.example.plugins

import com.example.controllers.*
import com.example.utils.mqtt.AtMostOnce
import com.example.utils.mqtt.topic

import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking

fun Application.configureRouting() {
    routing {
        route("/api") {
            taskRouting()
            firestationRouting()
            firetruckRouting()
            firemanRouting()
            skillRouting()
            firemanSkillRouting()
            missionRouting()
            fireRouting()
            intervenesRouting()
        }
    }
}
