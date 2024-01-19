package br.com.leandroconte.services.impl

import br.com.leandroconte.dao.CastlesDAO
import br.com.leandroconte.dao.impl.CastlesDAOImpl
import br.com.leandroconte.dto.CastleDTO
import br.com.leandroconte.models.Castle
import br.com.leandroconte.routes.castles.command.CreateCastleCommand
import br.com.leandroconte.services.CastleService
import org.jetbrains.exposed.sql.transactions.transaction

class CastleServiceImpl: CastleService {

    val dao: CastlesDAO = CastlesDAOImpl()

    override suspend fun create(createCastleCommand: CreateCastleCommand) {
        transaction {
            val castle = Castle(
                name = createCastleCommand.name,
                lat = createCastleCommand.lat,
                lng = createCastleCommand.lng
            )

            dao.addCastle(castle)
        }
    }

    override suspend fun list(): List<Castle> {
        var castles = emptyList<Castle>()
        transaction {
            castles = dao.allCastles()
                .map { Castle(it.id.value, it.name, it.lat, it.lng) }
                .toList()
        }

        return castles
    }

}