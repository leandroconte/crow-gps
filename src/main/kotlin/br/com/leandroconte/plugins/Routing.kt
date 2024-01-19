package br.com.leandroconte.plugins

import br.com.leandroconte.routes.castles.castlesRouting
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import br.com.leandroconte.routes.crow.crowRouting

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        crowRouting()
        castlesRouting()
    }
}
