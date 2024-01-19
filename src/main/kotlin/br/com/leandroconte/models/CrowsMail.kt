package br.com.leandroconte.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable


object CrowsMail : LongIdTable() {
    val crowName = varchar("crow_name", 128)
    val originCastle = reference("origin_castle", Castles)
    val destinationCastle = reference("destination_castle", Castles)
}

class CrowMailEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<CrowMailEntity>(CrowsMail)
    var crowName by CrowsMail.crowName
    var originCastle by CastleEntity referencedOn CrowsMail.originCastle
    var destinationCastle by CastleEntity referencedOn CrowsMail.destinationCastle
}

@Serializable
data class CrowMail(val id: Long?, val crowName: String, val originCastle: Castle, val destinationCastle: Castle)
