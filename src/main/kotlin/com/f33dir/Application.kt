package com.f33dir

import com.f33dir.API.Quotation.QuotationController
import com.f33dir.API.User.UserController
import com.f33dir.DI.mainModule
import com.f33dir.Routing.staticPages
import com.f33dir.assets.configureAssets
import com.f33dir.plugins.*
import io.ktor.resources.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.application.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) : Unit = EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation){
        json()
    }
    install(Koin){
        modules(mainModule)
    }
    configureSecurity()
    configureDatabases()
    staticPages()
    configureHTTP()
    configureTemplating()
    configureSerialization()
    configureAssets()
    UserController()
    QuotationController()
}
