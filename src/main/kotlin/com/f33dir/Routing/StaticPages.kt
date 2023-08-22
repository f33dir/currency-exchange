package com.f33dir.Routing

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.staticPages(){
    routing {
        authenticate("auth-session") {
            get("/") {
                call.respond(FreeMarkerContent("index.ftl", mapOf("content" to "main","logged" to true ), ""))
            }
        }
        get("/login") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("content" to "login","logged" to false),""))
        }
        get("/register") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("content" to "register","logged" to false),""))
        }
    }
}