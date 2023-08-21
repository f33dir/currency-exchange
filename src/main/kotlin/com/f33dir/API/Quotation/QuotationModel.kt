package com.f33dir.API.Quotation

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuotationModel(
    @SerialName("Id")
    val id : String = "",
    @SerialName("NumCode")
    val numCode : String,
    @SerialName("CharCode")
    val charCode: String,
    @SerialName("Nominal")
    val nominal : Int,
    @SerialName("Name")
    val name : String,
    @SerialName("Value")
    val value : Double,
    @SerialName("Previous")
    val previous: Double
    ){
}
@Serializable
data class QuotationMetaData(
    val date : Instant,
    val previousDate :Instant,
    val previousUrl: String,
    val timestamp: Instant
)
@Serializable
data class QuotationCollectionModel(
    @SerialName("Valute")
    var valute: HashMap<String,QuotationModel>,
    @SerialName("Date")
    val date : Instant,
    @SerialName("PreviousDate")
    val previousDate :Instant,
    @SerialName("PreviousURL")
    val previousUrl: String,
    @SerialName("Timestamp")
    val timestamp: Instant
)