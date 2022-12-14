package com.example.controllers

import com.example.dao.FiretruckDao
import com.example.tables.Firetruck
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Route.firetruckRouting() {
    route("/firetruck") {
        post {
            val params = call.receive<Firetruck>()
            call.respond(FiretruckDao.addFiretruck(params.firestation_id, params.coords))
        }

        get {
            call.respond(FiretruckDao.getAllFiretrucks())
        }

        get("/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val firetruck = FiretruckDao.getFiretruck(id)

            if (firetruck == null) {
                call.respondText("FIRETRUCK NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(firetruck)
            }
        }

        post("/{id}/update") {
            val params = call.receive<Firetruck>()
            val id = call.parameters.getOrFail<Int>("id").toInt()

            if (!FiretruckDao.firetruckExists(id)) {
                call.respondText("FIRETRUCK NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(FiretruckDao.updateFiretruck(id, params.firestation_id, params.coords))
            }
        }

        get("/{id}/delete") {
            val id = call.parameters.getOrFail<Int>("id").toInt()

            if (!FiretruckDao.firetruckExists(id)) {
                call.respondText("FIRETRUCK NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(FiretruckDao.deleteFiretruck(id))
            }
        }
    }
}