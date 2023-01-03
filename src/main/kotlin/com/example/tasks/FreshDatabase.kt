package com.example.tasks

import com.example.utils.database.DatabaseFactory
import com.example.utils.database.DatabaseFactory.query
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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