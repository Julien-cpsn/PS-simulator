package com.example.tasks

import com.example.tables.*
import com.example.utils.database.DatabaseFactory
import com.example.utils.database.DatabaseFactory.query
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.SchemaUtils

object FreshDatabase {
  fun run() = runBlocking {
      launch {
          fresh()
      }
  }

    private suspend fun fresh() = query {
        DatabaseFactory.dropTables()
        DatabaseFactory.createTables()
    }
}