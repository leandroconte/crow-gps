package br.com.leandroconte.services.impl

import br.com.leandroconte.models.CrowPosition
import br.com.leandroconte.models.LatLng
import br.com.leandroconte.services.CoordinateCalculatorService
import br.com.leandroconte.services.CrowService
import org.koin.core.annotation.Single

@Single
class CoordinateCalculatorServiceImpl(val crowService: CrowService): CoordinateCalculatorService {

    override suspend fun calculateNextCoord(idCrow: Long, curLat: Double, curLng: Double): LatLng {
        val crowMail = crowService.get(idCrow)
        val destLat = crowMail.destinationCastle.lat
        val destLng = crowMail.destinationCastle.lng

        var diffLat = destLat - curLat
        var diffLng = destLng - curLng

        if (diffLat <= 0) {
            diffLat *= -1
        }

        if (diffLng <= 0) {
            diffLng *= -1
        }

        // TODO: 1 degree lat is X km. Convert km to ms
        val velocity = 1.0000001
        var multiplierLng = velocity
        var multiplierLat = velocity

        val diffLatLng = diffLat / diffLng
        if (diffLatLng > 0) {
            multiplierLng = (diffLat / diffLng) * velocity
        } else {
            multiplierLat = (diffLng / diffLat) * velocity
        }

        val nextLat = curLat + multiplierLat
        val nextLng = curLng + multiplierLng

        return LatLng(nextLat, nextLng)
    }

    override suspend fun reached(crowPosition: CrowPosition): Boolean {
        val crowMail = crowService.get(crowPosition.idCrow)
        val destinationCastle = crowMail.destinationCastle

        return (destinationCastle.lat - crowPosition.latLng.lat <= 0
                && destinationCastle.lng - crowPosition.latLng.lng <= 0)
    }

}