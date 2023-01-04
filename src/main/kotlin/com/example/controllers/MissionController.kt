package com.example.controllers

import com.example.dao.MissionDao
import com.example.tables.Mission
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import kotlinx.datetime.toJavaLocalDateTime

fun Route.missionRouting() {
    route("/mission") {
        post {
            val params = call.receive<Mission>()
            call.respond(MissionDao.addMission(params.start.toJavaLocalDateTime(), params.end?.toJavaLocalDateTime(), params.comment))
        }

        get {
            call.respond(MissionDao.getAllMissions())
        }

        get("/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val mission = MissionDao.getMission(id)

            if (mission == null) {
                call.respondText("MISSION NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(mission)
            }
        }

        post("/{id}/update") {
            val params = call.receive<Mission>()
            val id = call.parameters.getOrFail<Int>("id").toInt()

            if (!MissionDao.missionsExists(id)) {
                call.respondText("MISSION NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(MissionDao.updateMission(id, params.start.toJavaLocalDateTime(), params.end?.toJavaLocalDateTime(), params.comment))
            }
        }

        get("/{id}/delete") {
            val id = call.parameters.getOrFail<Int>("id").toInt()

            if (!MissionDao.missionsExists(id)) {
                call.respondText("MISSION NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(MissionDao.deleteMission(id))
            }
        }
    }
}