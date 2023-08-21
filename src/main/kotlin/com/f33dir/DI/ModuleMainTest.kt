package com.f33dir.DI

import com.f33dir.API.Quotation.QuotationRepo
import com.f33dir.API.User.UserRepo
import com.f33dir.plugins.connectToPostgres
import io.ktor.http.*
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.get
import java.sql.Connection


val mainModuleTest = module {
    single<Connection> { connectToPostgres(true) }
    single<UserRepo> { UserRepo(get())}
    single<QuotationRepo> {QuotationRepo(get())}
}