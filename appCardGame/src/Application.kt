package com.app

import com.app.repository.CardRepository
import com.app.repository.DatabaseFactory
import com.app.routes.cardTab
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.auth.*
import io.ktor.gson.*
import io.ktor.features.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(CORS) {
        method(HttpMethod.Delete)
        anyHost()
    }

    DatabaseFactory.init()
    val db = CardRepository()
    install(Locations) {
    }

    install(Authentication) {
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {
        cardTab(db)
        }
    }

const val API_VERSION = "v1/"

