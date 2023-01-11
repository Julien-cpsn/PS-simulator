package com.example.controllers

import com.example.dao.IntervenesDao
import com.example.tables.Intervene
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import kotlinx.datetime.toJavaLocalDateTime

fun Route.intervenesRouting() {
    route("/intervene") {
        post {
            val params = call.receive<Intervene>()
            call.respond(IntervenesDao.addIntervene(params.firetruck_id, params.fire_id, params.start.toJavaLocalDateTime(), params.end?.toJavaLocalDateTime(), params.comment))
        }

        get {
            call.respond(IntervenesDao.getAllIntervenes())
        }

        get("/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val intervene = IntervenesDao.getIntervene(id)

            if (intervene == null) {
                call.respondText("INTERVENE NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(intervene)
            }
        }

        post("/{id}/update") {
            val params = call.receive<Intervene>()
            val id = call.parameters.getOrFail<Int>("id").toInt()

            if (!IntervenesDao.interveneExists(id)) {
                call.respondText("INTERVENE NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(IntervenesDao.updateIntervene(id, params.firetruck_id, params.fire_id, params.start.toJavaLocalDateTime(), params.end?.toJavaLocalDateTime(), params.comment))
            }
        }

        get("/{id}/delete") {
            val id = call.parameters.getOrFail<Int>("id").toInt()

            if (!IntervenesDao.interveneExists(id)) {
                call.respondText("INTERVENE NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(IntervenesDao.deleteIntervene(id))
            }
        }
    }
}