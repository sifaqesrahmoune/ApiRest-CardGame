package com.app.routes

import com.app.API_VERSION
import com.app.Cards
import com.app.model.CardsGenerator

import com.app.repository.CardRepository
//import com.app.repository.CardRepository
import io.ktor.locations.*
import io.ktor.http.HttpStatusCode

import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.response.*


@OptIn(KtorExperimentalLocationsAPI::class)

fun Route.cardTab(
    db: CardRepository
) {

    /**
    get<CreateCard> {
        try {
            val cardList = db.getAllCardTab()
            if (cardList != null) {
                if (cardList.isNotEmpty()) {
                    call.respond(cardList)
                } else {
                    call.respondText("No Data found!!")
                }
            }
        } catch (e: Throwable) {
            application.log.error("Failed to register card", e)
            call.respond(HttpStatusCode.BadRequest, "Problems creating Card")
        }
    }**/

    post("$API_VERSION/cardTab") {
        val parameter = call.parameters["nb"]
        try {
            val nb = parameter!!.toInt()
            val cardList = CardsGenerator().createPackage(nb)
            val cardListStr = cardList.map { v -> v.toString() }.toList()
            val separator = "-"
            val mescartes = cardListStr.joinToString(separator)
            var defausse = ""

            val cardTab = db.insert(defausse, mescartes)
            if (cardTab != null) {
                Cards(cardTab).let {
                    call.respond(status = HttpStatusCode.OK, it)
                }
            }

        } catch (e: Throwable) {
            call.respondText("${e.message}")
        }
    }

    get("$API_VERSION/cardTab") {
        val parameter = call.parameters["id"]
        try {
            val cardTab = parameter?.toInt()?.let { it1 -> db.getCardTabById(it1) } ?: return@get call.respondText(
                "invalid id",
                status = HttpStatusCode.BadRequest
            )
            Cards(cardTab).let {
                call.respond(status = HttpStatusCode.OK, it)
            }
        } catch (e: Throwable) {
            application.log.error("Failed to register cardTab", e)
            call.respond(HttpStatusCode.BadRequest, "Problems creating cardTab")
        }
    }

    delete("$API_VERSION/cardTab") {
        val parameter = call.parameters["id"]
        try {
            val cardTab =
                parameter?.toInt()?.let { it1 -> db.deleteCardTabById(it1) } ?: return@delete call.respondText(
                    "no id found..",
                    status = HttpStatusCode.BadRequest
                )
            if (cardTab == 1) {
                call.respondText("deleted successfully", status = HttpStatusCode.OK)
            } else {
                call.respondText("id not found", status = HttpStatusCode.BadRequest)
            }

        } catch (e: Throwable) {
            application.log.error("Failed to register cardtab", e)
            call.respond(HttpStatusCode.BadRequest, "Problems creating CardTab")
        }

    }

    post("$API_VERSION/cardTab/shuffle") {
        val parameter = call.parameters["id"]
        try {
            val cardTab = parameter?.toInt()?.let { it1 -> db.getCardTabById(it1) } ?: return@post call.respondText(
                "invalid id",
                status = HttpStatusCode.BadRequest
            )
            Cards(cardTab).let {
                it.shuffle()
                db.update(it.cardTabId,it.defausse.joinToString("-"),it.mescartes.joinToString("-"))
                call.respond(status = HttpStatusCode.OK, it)
            }

        } catch (e: Throwable) {
            application.log.error("Failed to register cardTab", e)
            call.respond(HttpStatusCode.BadRequest, "Problems creating cardTab")
        }
    }

    post ("$API_VERSION/cardTab/discardFirstCard"){
        val parameter = call.parameters["id"]
        try {
            val cardTab = parameter?.toInt()?.let { it1 -> db.getCardTabById(it1) } ?: return@post call.respondText(
                "invalid id",
                status = HttpStatusCode.BadRequest
            )
            Cards(cardTab).let {
                it.discardFirst()
                db.update(it.cardTabId,it.defausse.joinToString("-"),it.mescartes.joinToString("-"))
                call.respond(status = HttpStatusCode.OK, it)
            }

        } catch (e: Throwable) {
            application.log.error("Failed to register cardTab", e)
            call.respond(HttpStatusCode.BadRequest, "Problems creating cardTab")
        }
    }

    post ("$API_VERSION/cardTab/putDiscardCardOnGame"){
        val parameter = call.parameters["id"]
        try {
            val cardTab = parameter?.toInt()?.let { it1 -> db.getCardTabById(it1) } ?: return@post call.respondText(
                "invalid id",
                status = HttpStatusCode.BadRequest
            )
            Cards(cardTab).let {
                it.putDiscardCard()
                db.update(it.cardTabId,it.defausse.joinToString("-"),it.mescartes.joinToString("-"))
                call.respond(status =HttpStatusCode.OK, it)
            }

        } catch (e: Throwable) {
            application.log.error("Failed to register cardTab", e)
            call.respond(HttpStatusCode.BadRequest, "Problems creating cardTab")
        }
    }




}