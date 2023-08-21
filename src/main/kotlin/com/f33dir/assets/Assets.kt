package com.f33dir.assets

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureAssets(){
    routing {
        staticResources("/assets/img/","assets/img")
        staticResources("/assets/js/","assets/js") {
            contentType{ContentType.Application.JavaScript}
        }
    }
}