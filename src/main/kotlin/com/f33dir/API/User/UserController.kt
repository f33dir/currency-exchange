package com.f33dir.API.User

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.f33dir.plugins.MySession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.koin.ktor.ext.inject
import org.mindrot.jbcrypt.BCrypt


fun Application.UserController() {
    val userRepo : UserRepo by inject()
    routing {

            post("/api/register"){
                val credentials = call.receive<User>()
                val test = userRepo.getUserByLogin(credentials)
                if(test.id==null){
                    val result = userRepo.createUser(credentials)
                    call.response.status(HttpStatusCode.Created)
                    call.respond("done")
                } else {
                    call.response.status(HttpStatusCode.Conflict)
                    call.respond("Пользователь с таким именем уже существует")
                }
            }
            post("/api/login"){
                val credentials = call.receive<User>()
                val user = userRepo.getUserByLogin(credentials)
                if(user.id != null && BCrypt.checkpw(credentials.password, user.password)){
                    call.response.status(HttpStatusCode.OK)
                    call.sessions.set(MySession(username = user.login, isAdmin = user.isAdmin))
                    call.respond("")
                } else{
                    call.response.status(HttpStatusCode.Unauthorized)
                    call.respond("Неправильный логин или пароль")
                }
            }
        authenticate("auth-session") {
            post("/api/logout"){
                call.sessions.clear<MySession>()
                call.respondRedirect("/login")
            }
        }
    }
}