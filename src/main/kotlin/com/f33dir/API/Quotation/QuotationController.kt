package com.f33dir.API.Quotation

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.QuotationController(){
    val quotationRepo : QuotationRepo by inject()
    val quotationService = QuotationService(quotationRepo = quotationRepo)
    routing {
        authenticate("auth-session-api") {
            get("/api/quotation"){
                quotationService.fetchQuotations()
                var quotations = quotationRepo.getQuotations()
                call.respond(quotations);
            }
        }
    }

}