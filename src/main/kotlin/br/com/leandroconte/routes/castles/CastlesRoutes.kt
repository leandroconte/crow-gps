package br.com.leandroconte.routes.castles

import br.com.leandroconte.routes.castles.command.CreateCastleCommand
import br.com.leandroconte.services.CastleService
import br.com.leandroconte.services.impl.CastleServiceImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.castlesRouting() {

    val castleService: CastleService = CastleServiceImpl()

    route("/castles") {

        post {
            val createCastleCommand = call.receive<CreateCastleCommand>()
            castleService.create(createCastleCommand)
            call.respond(HttpStatusCode.OK)
        }

        get {
            call.respond(castleService.list())
        }

    }
}
