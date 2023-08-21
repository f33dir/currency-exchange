package com.f33dir

import com.f33dir.API.Quotation.QuotationRepo
import com.f33dir.API.User.User
import com.f33dir.API.User.UserRepo
import com.f33dir.DI.mainModule
import com.f33dir.DI.mainModuleTest
import com.f33dir.Routing.staticPages
import com.f33dir.plugins.configureDatabases
import com.f33dir.plugins.configureSecurity
import com.f33dir.plugins.connectToPostgres
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.buildJsonObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.koin.java.KoinJavaComponent
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

//import org.junit.jupiter.api.Test
class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
//            configureSecurity()
            configureDatabases()
            staticPages()
//            install(Koin){
//                modules(mainModuleTest)
//            }
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
//            assertEquals("Hello World!",    bodyAsText())
        }
        client.get("/login").apply {
            assertEquals(HttpStatusCode.OK, status)
//            assertEquals("Hello World!",    bodyAsText())
        }
        client.get("/register").apply {
            assertEquals(HttpStatusCode.OK, status)
//            assertEquals("Hello World!",    bodyAsText())
        }
    }

    @Test
    fun testApiQuotations()=
         testApplication {
             assertEquals( client.get("/API/quotation").apply{
                 assertEquals(HttpStatusCode.Forbidden ,status);
             }.status,HttpStatusCode.Forbidden)
        }
    }



