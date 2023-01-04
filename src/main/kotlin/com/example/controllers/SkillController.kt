package com.example.controllers

import com.example.dao.SkillDao
import com.example.tables.Skill
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Route.skillRouting() {
    route("/skill") {
        post {
            val params = call.receive<Skill>()
            call.respond(SkillDao.addSkill(params.name))
        }

        get {
            call.respond(SkillDao.getAllSkills())
        }

        get("/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val skill = SkillDao.getSkill(id)

            if (skill == null) {
                call.respondText("SKILL NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(skill)
            }
        }

        post("/{id}/update") {
            val params = call.receive<Skill>()
            val id = call.parameters.getOrFail<Int>("id").toInt()

            if (!SkillDao.skillExists(id)) {
                call.respondText("SKILL NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(SkillDao.updateSkill(id, params.name))
            }
        }

        get("/{id}/delete") {
            val id = call.parameters.getOrFail<Int>("id").toInt()

            if (!SkillDao.skillExists(id)) {
                call.respondText("SKILL NOT FOUND", status = HttpStatusCode.NotFound)
            }
            else {
                call.respond(SkillDao.deleteSkill(id))
            }
        }
    }
}