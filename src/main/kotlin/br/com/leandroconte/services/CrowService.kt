package br.com.leandroconte.services

import br.com.leandroconte.models.CrowMail
import br.com.leandroconte.routes.crow.command.SendCrowMailCommand

interface CrowService {

    suspend fun sendMessage(sendCrowMailCommand: SendCrowMailCommand)
    suspend fun list(): List<CrowMail>

}