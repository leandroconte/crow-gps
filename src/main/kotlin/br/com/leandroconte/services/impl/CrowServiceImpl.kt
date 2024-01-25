package br.com.leandroconte.services.impl

import br.com.leandroconte.anticorruption.kafka.producer.CrowProducer
import br.com.leandroconte.models.*
import br.com.leandroconte.repository.CastlesRepository
import br.com.leandroconte.repository.CrowsRepository
import br.com.leandroconte.routes.crow.command.SendCrowMailCommand
import br.com.leandroconte.services.CrowService
import com.google.gson.Gson
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.annotation.Single

@Single
class CrowServiceImpl(
    val crowsRepository: CrowsRepository,
    val castlesRepository: CastlesRepository,
    val crowProducer: CrowProducer
) : CrowService {

    val gson: Gson = Gson()

    override suspend fun sendMessage(sendCrowMailCommand: SendCrowMailCommand) {
        transaction {
            val randomCrowName = getRandomCrowName()
            val origCastle = castlesRepository.castle(sendCrowMailCommand.idOriginCastle)?.toCastle()
            val destCastle = castlesRepository.castle(sendCrowMailCommand.idDestinationCastle)?.toCastle()

            val crowMail = CrowMail(
                id = null,
                crowName = randomCrowName,
                originCastle = origCastle!!,
                destinationCastle = destCastle!!
            )

            val idCrow = crowsRepository.addCrowMail(crowMail).id.value
            val crowPosition = CrowPosition(idCrow, LatLng(origCastle.lat, origCastle.lng))
            crowProducer.produceMessages(gson.toJson(crowPosition))
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

    override suspend fun get(id: Long): CrowMail {
        var crowMail: CrowMail? = null

        transaction {
            val crowMailEntity = crowsRepository.crowMail(id)
            crowMailEntity ?: throw IllegalArgumentException("Crow not found")
            crowMail = toCrowMail(crowMailEntity)
        }

        return crowMail!!


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