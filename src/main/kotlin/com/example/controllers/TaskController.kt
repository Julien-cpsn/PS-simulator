package com.example.controllers

import com.example.tasks.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.launch

// https://kotlinlang.org/docs/command-line.html
fun Route.taskRouting() {
    route("task") {
        route("/database") {
            get("/fresh") {
                FreshDatabase.run()
                var seeding = ""
                if (call.parameters["seed"].toBoolean()) {
                    seeding = "and seeding "
                    SeedDatabase.run()
                }
                call.respondText("Refreshing ${seeding}database...", status = HttpStatusCode.OK)
            }
            get("/seed") {
                SeedDatabase.run()
                call.respondText("Seeding database...", status = HttpStatusCode.OK)
            }
        }

        route("/simulator") {
            get("/start") {
                Simulator.run()
                call.respondText("Simulator is running...")
            }

            get("/stop") {
                Simulator.stop()
                call.respondText("Simulator is stopping...")
            }
        }
    }
}