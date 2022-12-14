package com.example.plugins

import com.example.controllers.*

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("/api") {
            taskRouting()
            firestationRouting()
            firetruckRouting()
            firemanRouting()
        }
    }
}
