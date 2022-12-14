package com.example.utils.database

import com.example.tables.Firestations
import io.ktor.server.config.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*

object DatabaseFactory {
    fun init(config: ApplicationConfig) {
        val driverClassName = config.property("ktor.database.driver").getString()
        val jdbcURL = config.property("ktor.database.host").getString()
        val username = config.property("ktor.database.user").getString()
        val password = config.property("ktor.database.password").getString()
        val defaultDatabase = config.property("ktor.database.database").getString()
        val database = Database.connect(
            url = "$jdbcURL/$defaultDatabase",
            driver = driverClassName,
            user = username,
            password = password
        )

        transaction(database) {
            SchemaUtils.create(Firestations)
            //SchemaUtils.create(Users)
        }
    }

    suspend fun <T> query(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}