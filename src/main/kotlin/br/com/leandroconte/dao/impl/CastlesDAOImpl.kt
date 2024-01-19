package br.com.leandroconte.dao.impl

import br.com.leandroconte.dao.CastlesDAO
import br.com.leandroconte.models.Castle
import br.com.leandroconte.models.CastleEntity

class CastlesDAOImpl : CastlesDAO {
    override fun allCastles(): List<CastleEntity> = CastleEntity.all().toList()

    override fun castle(id: Long): CastleEntity? = CastleEntity.findById(id)

    override fun addCastle(castle: Castle) {
        CastleEntity.new {
            name = castle.name
            lat = castle.lat
            lng = castle.lng
        }
    }
}