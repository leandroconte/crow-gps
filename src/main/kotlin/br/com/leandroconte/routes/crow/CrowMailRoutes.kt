package br.com.leandroconte.routes.crow

import br.com.leandroconte.routes.crow.command.SendCrowMailCommand
import br.com.leandroconte.services.CrowService
import br.com.leandroconte.services.impl.CrowServiceImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.crowRouting() {

    val crowService: CrowService = CrowServiceImpl()

    route("/crows") {

        post("/send-message") {
            val sendCrowMailCommand = call.receive<SendCrowMailCommand>()
            crowService.sendMessage(sendCrowMailCommand)
            call.respond(HttpStatusCode.OK)
        }

        get {
            call.respond(crowService.list())
        }

    }
}
