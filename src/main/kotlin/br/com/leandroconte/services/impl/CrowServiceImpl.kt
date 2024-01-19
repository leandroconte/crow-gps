package br.com.leandroconte.services.impl

import br.com.leandroconte.dao.CastlesDAO
import br.com.leandroconte.dao.CrowsDAO
import br.com.leandroconte.dao.impl.CastlesDAOImpl
import br.com.leandroconte.dao.impl.CrowsDAOImpl
import br.com.leandroconte.models.Castle
import br.com.leandroconte.models.CrowMail
import br.com.leandroconte.models.CrowMailEntity
import br.com.leandroconte.routes.crow.command.SendCrowMailCommand
import br.com.leandroconte.services.CrowService
import org.jetbrains.exposed.sql.transactions.transaction

class CrowServiceImpl : CrowService {

    val crowsDao: CrowsDAO = CrowsDAOImpl();
    val castlesDao: CastlesDAO = CastlesDAOImpl();

    override suspend fun sendMessage(sendCrowMailCommand: SendCrowMailCommand) {
        transaction {
            val randomCrowName = getRandomCrowName()
            val origCastle = castlesDao.castle(sendCrowMailCommand.idOriginCastle)?.toCastle()
            val destCastle = castlesDao.castle(sendCrowMailCommand.idDestinationCastle)?.toCastle()

            val crowMail = CrowMail(
                id = null,
                crowName = randomCrowName,
                originCastle = origCastle!!,
                destinationCastle = destCastle!!
            )

            crowsDao.addCrowMail(crowMail)
        }
    }

    override suspend fun list(): List<CrowMail> {
        var crows = emptyList<CrowMail>()

        transaction {
            crows = CrowMailEntity.all()
                .map { toCrowMail(it) }
                .toList()
        }

        return crows
    }

    private fun toCrowMail(it: CrowMailEntity) = CrowMail(
        id = it.id.value,
        crowName = it.crowName,
        originCastle = Castle(it.originCastle.id.value, it.originCastle.name, it.originCastle.lat, it.originCastle.lng),
        destinationCastle = Castle(
            it.destinationCastle.id.value,
            it.destinationCastle.name,
            it.destinationCastle.lat,
            it.destinationCastle.lng
        )
    )

    private fun getRandomCrowName(): String {
        return "Jubileu"
    }
}