package com.example.controllers

import com.example.dao.FiremanDao
import com.example.tables.Fireman
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Route.firemanRouting() {
    route("/fireman") {
        post {
            val params = call.receive<Fireman>()
            call.respond(FiremanDao.addFireman(params.firestation_id, params.firetruck_id, params.first_name, params.last_name))
        }

        get {
            call.respond(FiremanDao.getAllFiremans())
        }

        get("/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val fireman = FiremanDao.getFireman(id)

            if (fireman == null) {
                call.respondText("FIREMAN NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(fireman)
            }
        }

        post("/{id}/update") {
            val params = call.receive<Fireman>()
            val id = call.parameters.getOrFail<Int>("id").toInt()

            if (!FiremanDao.firemanExists(id)) {
                call.respondText("FIREMAN NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(FiremanDao.updateFireman(id, params.firestation_id, params.firetruck_id, params.first_name, params.last_name))
            }
        }

        get("/{id}/delete") {
            val id = call.parameters.getOrFail<Int>("id").toInt()

            if (!FiremanDao.firemanExists(id)) {
                call.respondText("FIREMAN NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(FiremanDao.deleteFireman(id))
            }
        }
    }
}