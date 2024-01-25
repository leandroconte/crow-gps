package br.com.leandroconte.services.impl

import br.com.leandroconte.models.Castle
import br.com.leandroconte.repository.CastlesRepository
import br.com.leandroconte.routes.castles.command.CreateCastleCommand
import br.com.leandroconte.services.CastleService
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.annotation.Single

@Single
class CastleServiceImpl(val castlesRepository: CastlesRepository): CastleService {

    override suspend fun create(createCastleCommand: CreateCastleCommand) {
        transaction {
            val castle = Castle(
                name = createCastleCommand.name,
                lat = createCastleCommand.lat,
                lng = createCastleCommand.lng
            )

            castlesRepository.addCastle(castle)
        }
    }

    override suspend fun list(): List<Castle> {
        var castles = emptyList<Castle>()
        transaction {
            castles = castlesRepository.allCastles()
                .map { Castle(it.id.value, it.name, it.lat, it.lng) }
                .toList()
        }

        return castles
    }

}