package br.com.leandroconte.repository

import br.com.leandroconte.models.Castle
import br.com.leandroconte.models.CastleEntity

interface CastlesRepository {
    fun allCastles(): List<CastleEntity>
    fun castle(id: Long): CastleEntity?
    fun addCastle(castle: Castle)
}
