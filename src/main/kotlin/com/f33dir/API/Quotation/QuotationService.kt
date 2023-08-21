package com.f33dir.API.Quotation

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import java.time.Duration
import java.time.Instant
import kotlin.math.abs

class QuotationService(val quotationRepo: QuotationRepo) {

    private var lastUpdate = Instant.ofEpochSecond(0)
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            Json {
                prettyPrint = true
                isLenient = true
            }
            register(
                ContentType.Application.JavaScript,KotlinxSerializationConverter(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            )
        }
    }

    suspend fun fetchQuotations() {
        try {
            val response :QuotationCollectionModel = client.get("https://www.cbr-xml-daily.ru/daily_json.js").body()
            if(abs(Duration.between(Instant.now(),lastUpdate).seconds) >600){
                lastUpdate = Instant.now()
                if (quotationRepo.getLastUpdateTime().epochSecond != response.date.epochSeconds){
                    quotationRepo.updateQuotations(response)
                }
            }
        }  catch(error:Error){
            throw error;
        }
    }
}