package com.f33dir.API.Quotation

import kotlinx.datetime.toJavaInstant
import java.sql.Connection
import java.sql.Statement
import java.sql.Timestamp
import java.time.Instant

class QuotationRepo(private val connection: Connection) {
    companion object {
        private const val UPDATE_QUOTATIONS = "INSERT INTO \"Quotations\"(\"CharCode\",\"NumCode\",\"Name\",\"Value\",\"Previous\",\"Date\") Values (?,?,?,?,?,?)"
        private const val GET_LAST_UPDATE_DATETIME = "SELECT \"Date\" FROM \"Quotations\" ORDER BY \"Date\" DESC"
        private const val GET_QUOTATIONS ="SELECT DISTINCT * FROM \"Quotations\" WHERE \"Date\"=(SELECT max(\"Date\") from \"Quotations\" ) "
    }
    suspend fun updateQuotations(quotations : QuotationCollectionModel): Boolean{
        connection.autoCommit = false;
        val statement = connection.prepareStatement(UPDATE_QUOTATIONS, Statement.RETURN_GENERATED_KEYS)
        quotations.valute.forEach { quotationModel ->
            statement.setString(1,quotationModel.value.charCode)
            statement.setString(2,quotationModel.value.numCode)
            statement.setString(3,quotationModel.value.name)
            statement.setDouble(4,quotationModel.value.value/quotationModel.value.nominal)
            statement.setDouble(5,quotationModel.value.previous/quotationModel.value.nominal)
            statement.setTimestamp(6, Timestamp.from(quotations.date.toJavaInstant()))
            statement.addBatch()
        }
        statement.executeBatch()
        connection.autoCommit = true;
        val generatedKeys = statement.generatedKeys;
        if (generatedKeys.next()) {
            return true;
        } else {
            throw Exception("Failed to retrieve id after inserting into Quotations")
        }
    }
    suspend fun getLastUpdateTime(): Instant{
        val statement = connection.prepareStatement(GET_LAST_UPDATE_DATETIME);
        val result = statement.executeQuery()
        return if(result.next()){
            result.getTimestamp("Date").toInstant()
        } else {
            Instant.ofEpochSecond(0)
        }
    }
    suspend fun getQuotations() :ArrayList<QuotationModel>{
        val statement =  connection.prepareStatement(GET_QUOTATIONS)
        val result = statement.executeQuery()
        val arr = ArrayList<QuotationModel>()
        while (result.next()){
            arr.add(
                QuotationModel(
                result.getString("Id"),
                result.getString("NumCode"),
                result.getString("CharCode"),
                1,
                result.getString("Name"),
                result.getDouble("Value"),
                result.getDouble("Previous")
                ))
        }
        arr.add(QuotationModel("0","810","RUB",1,"Российский рубль", 1.0,1.0))
        return arr;
    }
}