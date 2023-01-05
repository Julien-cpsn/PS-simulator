package com.example.controllers

import com.example.dao.FireDao
import com.example.tables.Fire
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Route.fireRouting() {
    route("/fire") {
        post {
            val params = call.receive<Fire>()
            call.respond(FireDao.addFire(params.mission_id, params.frequency, params.intensity, params.coords))
        }

        get {
            call.respond(FireDao.getAllFires())
        }

        get("/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val fire = FireDao.getFire(id)

            if (fire == null) {
                call.respondText("FIRE NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(fire)
            }
        }

        post("/{id}/update") {
            val params = call.receive<Fire>()
            val id = call.parameters.getOrFail<Int>("id").toInt()

            if (!FireDao.fireExists(id)) {
                call.respondText("FIRE NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(FireDao.updateFire(id, params.mission_id, params.frequency, params.intensity, params.coords))
            }
        }

        get("/{id}/delete") {
            val id = call.parameters.getOrFail<Int>("id").toInt()

            if (!FireDao.fireExists(id)) {
                call.respondText("FIRE NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(FireDao.deleteFire(id))
            }
        }
    }
}