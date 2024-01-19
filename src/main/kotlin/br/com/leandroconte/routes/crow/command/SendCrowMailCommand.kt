package br.com.leandroconte.routes.crow.command

import kotlinx.serialization.Serializable

@Serializable
data class SendCrowMailCommand(val idOriginCastle: Long, val idDestinationCastle: Long)
