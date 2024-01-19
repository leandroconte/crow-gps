package br.com.leandroconte.services

import br.com.leandroconte.models.Castle
import br.com.leandroconte.routes.castles.command.CreateCastleCommand

interface CastleService {

    suspend fun create(createCastleCommand: CreateCastleCommand)
    suspend fun list(): List<Castle>

}