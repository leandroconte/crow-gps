package br.com.leandroconte.dao.impl

import br.com.leandroconte.dao.CrowsDAO
import br.com.leandroconte.models.CastleEntity
import br.com.leandroconte.models.CrowMail
import br.com.leandroconte.models.CrowMailEntity

class CrowsDAOImpl : CrowsDAO {
    override fun allCrowsMail(): List<CrowMailEntity> = CrowMailEntity.all().toList()

    override fun crowMail(id: Long): CrowMailEntity? = CrowMailEntity.findById(id)

    override fun addCrowMail(crowMail: CrowMail) {

        CrowMailEntity.new {
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