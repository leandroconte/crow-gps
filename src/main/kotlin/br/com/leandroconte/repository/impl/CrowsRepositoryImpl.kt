package br.com.leandroconte.repository.impl

import br.com.leandroconte.models.CastleEntity
import br.com.leandroconte.models.CrowMail
import br.com.leandroconte.models.CrowMailEntity
import br.com.leandroconte.repository.CrowsRepository
import org.koin.core.annotation.Single

@Single
class CrowsRepositoryImpl : CrowsRepository {
    override fun allCrowsMail(): List<CrowMailEntity> = CrowMailEntity.all().toList()

    override fun crowMail(id: Long): CrowMailEntity? = CrowMailEntity.findById(id)

    override fun addCrowMail(crowMail: CrowMail): CrowMailEntity {
        return CrowMailEntity.new {
            crowName = crowMail.crowName
            originCastle = CastleEntity[crowMail.originCastle.id!!]
            destinationCastle = CastleEntity[crowMail.destinationCastle.id!!]
        }
    }

//    private fun resultRowToCrowsMail(row: ResultRow) = CrowMail(
//        id = row[CrowsMail.id].value,
//        crowName = row[CrowsMail.crowName],
//        originCastle = resultRowToCastle(row),
//        destinationCastle = resultRowToCastle(row)
//    )
//
//    private fun resultRowToCastle(row: ResultRow) = Castle(
//        id = row[Castles.id].value,
//        name = row[Castles.name],
//        lat = row[Castles.lat],
//        lng = row[Castles.lng]
//    )
}