package br.com.leandroconte.dao

import br.com.leandroconte.models.Castle
import br.com.leandroconte.models.CastleEntity

interface CastlesDAO {
    fun allCastles(): List<CastleEntity>
    fun castle(id: Long): CastleEntity?
    fun addCastle(castle: Castle)
}
