package br.com.leandroconte.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object Castles: LongIdTable() {
    val name = varchar("name", 128)
    val lat = double("lat")
    val lng = double("lng")
}

class CastleEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<CastleEntity>(Castles)
    var name by Castles.name
    var lat by Castles.lat
    var lng by Castles.lng

    fun toCastle(): Castle {
        val castleEntity: CastleEntity = this
        return Castle(
            id = castleEntity.id.value,
            name = castleEntity.name,
            lat = castleEntity.lat,
            lng = castleEntity.lng
        )
    }
}

@Serializable
data class Castle(var id: Long? = null, val name: String, val lat: Double, val lng: Double)
