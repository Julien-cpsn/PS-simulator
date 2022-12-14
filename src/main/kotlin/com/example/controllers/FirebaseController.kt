package com.example.controllers

import com.example.dao.FirestationDao
import com.example.tables.Firestation
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Route.firestationRouting() {
    route("/firestation") {
        post {
            val params = call.receive<Firestation>()
            call.respond(FirestationDao.addFirestation(params.name, params.coords))
        }

        get {
            call.respond(FirestationDao.getAllFirestations())
        }

        get("/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val firestation = FirestationDao.getFirestation(id)

            if (firestation == null) {
                call.respondText("FIRESTATION NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(firestation)
            }
        }

        post("/{id}/update") {
            val params = call.receive<Firestation>()
            val id = call.parameters.getOrFail<Int>("id").toInt()

            if (!FirestationDao.firestationExists(id)) {
                call.respondText("FIRESTATION NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(FirestationDao.updateFirestation(id, params.name, params.coords))
            }
        }

        get("/{id}/delete") {
            val id = call.parameters.getOrFail<Int>("id").toInt()

            if (!FirestationDao.firestationExists(id)) {
                call.respondText("FIRESTATION NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(FirestationDao.deleteFirestation(id))
            }
        }
    }
}