package com.f33dir.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.*
import kotlinx.coroutines.*

fun Application.configureDatabases() {
    val dbConnection: Connection = connectToPostgres(embedded = false)
}

fun connectToPostgres(embedded: Boolean): Connection {
    Class.forName("org.postgresql.Driver")
    if (embedded) {
        return DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "root", "")
    } else {
        val url =
            System.getenv("DATABASE_URL")
        val user =
            System.getenv("DATABASE_USER")
        val password =
            System.getenv("DATABASE_PASSWORD")
        return DriverManager.getConnection(url, user, password)
    }
}
