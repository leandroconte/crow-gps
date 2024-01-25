package br.com.leandroconte.services

import br.com.leandroconte.models.CrowPosition
import br.com.leandroconte.models.LatLng

interface CoordinateCalculatorService {

    suspend fun calculateNextCoord(idCrow: Long, curLat: Double, curLng: Double): LatLng
    suspend fun reached(crowPosition: CrowPosition): Boolean

}