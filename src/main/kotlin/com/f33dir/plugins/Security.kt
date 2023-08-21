package com.f33dir.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import kotlinx.serialization.Serializable
import kotlin.collections.set

data class MySession(var username: String?, var isAdmin: Boolean?) :Principal

fun Application.configureSecurity() {
    @Serializable
    data class ApiStatus (val status: Boolean, val message : String){

    }
//    val jwtSecret = environment.config.property("jwt.secret").getString()
//    val jwtDomain = environment.config.property("jwt.issuer").getString()
//    val jwtAudience = environment.config.property("jwt.audience").getString()
//    val jwtRealm = environment.config.property("jwt.realm").getString()
    install(Sessions){
        val secretSignKey = "secret".toByteArray()
        cookie<MySession>("session", SessionStorageMemory()){
            cookie.path = "/"
            transform(SessionTransportTransformerMessageAuthentication(secretSignKey))
            cookie.extensions["SameSite"] = "strict"
        }
    }
    install(Authentication){
        session<MySession>("auth-session"){
            validate { session ->
                if(session.username!= null) {
                    session
                } else {
                    null
                }
            }
            challenge {
                call.respondRedirect("/login")
            }
        }
        session<MySession>("auth-session-api"){
            validate { session ->
                if(session.username!= null) {
                    session
                } else {
                    null
                }
            }
            challenge {
                call.response.status(HttpStatusCode.Forbidden)
                call.respond(ApiStatus(false,"Access denied"))
            }
        }
    }
}
