package br.com.leandroconte.models

import kotlinx.serialization.Serializable

@Serializable
data class CrowPosition(val idCrow: Long, val latLng: LatLng)
