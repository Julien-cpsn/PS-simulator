package com.example.controllers

import com.example.dao.FiremanSkillDao
import com.example.tables.FiremanSkill
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Route.firemanSkillRouting() {
    route("/fireman-skill") {
        post {
            val params = call.receive<FiremanSkill>()
            call.respond(FiremanSkillDao.addFiremanSkill(params.fireman_id, params.skill_id))
        }

        get {
            call.respond(FiremanSkillDao.getAllFiremansSkills())
        }

        get("/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val firemanSkill = FiremanSkillDao.getFiremanSkill(id)

            if (firemanSkill == null) {
                call.respondText("FIREMAN SKILL NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(firemanSkill)
            }
        }

        post("/{id}/update") {
            val params = call.receive<FiremanSkill>()
            val id = call.parameters.getOrFail<Int>("id").toInt()

            if (!FiremanSkillDao.firemanSkillExists(id)) {
                call.respondText("FIREMAN SKILL NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(FiremanSkillDao.updateFiremanSkill(id, params.fireman_id, params.skill_id))
            }
        }

        get("/{id}/delete") {
            val id = call.parameters.getOrFail<Int>("id").toInt()

            if (!FiremanSkillDao.firemanSkillExists(id)) {
                call.respondText("FIREMAN SKILL NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(FiremanSkillDao.deleteFiremanSkill(id))
            }
        }
    }
}