package br.com.leandroconte.routes.castles

import br.com.leandroconte.routes.castles.command.CreateCastleCommand
import br.com.leandroconte.services.CastleService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.castlesRouting() {

    val castleService: CastleService by inject()

    route("/castles") {

        post {
            try {
                val createCastleCommand = call.receive<CreateCastleCommand>()
                castleService.create(createCastleCommand)
                call.respond(HttpStatusCode.OK)
            } catch (e: Exception) {
                println(e)
            }
        }

        get {
            call.respond(castleService.list())
        }

    }
}
