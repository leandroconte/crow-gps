package br.com.leandroconte.routes.castles.command

import kotlinx.serialization.Serializable

@Serializable
data class CreateCastleCommand(val name: String, val lat: Double, val lng: Double)
