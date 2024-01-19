package br.com.leandroconte.dto

import kotlinx.serialization.Serializable

@Serializable
data class CastleDTO(val name: String, val lat: Double, val lng: Double)
