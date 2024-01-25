package br.com.leandroconte.anticorruption.kafka.consumer

import br.com.leandroconte.anticorruption.kafka.producer.CrowProducer
import br.com.leandroconte.models.CrowPosition
import br.com.leandroconte.services.CoordinateCalculatorService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_RECORDS_CONFIG
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.koin.core.annotation.Single
import java.time.Duration
import java.time.Duration.ofMillis
import java.time.LocalDateTime

private const val CLUSTER_API_KEY = "XR5RXAGTNZIIRWM6"
private const val CLUSTER_API_SECRET = "4Tzc8pTpBKEgrdeqzO3fFh/x95Q3DI9/y9edsLjRNXnwS06LhvbrsAKZ7VxI2CLY"

@Single(createdAtStart = true)
class CrowConsumer(
    val coordinateCalculatorService: CoordinateCalculatorService,
    val crowProducer: CrowProducer
) {

    val gson = Gson()

    init {
        consumeMessages()
    }

    private fun consumeMessages() {
        CoroutineScope(Dispatchers.Default).async {
            val topic = "topic_crows_gps"

            createConsumer().apply {
                subscribe(listOf(topic))
            }.use {
                while (true) {
                    it
                        .poll(ofMillis(100))
                        .mapNotNull { record -> calculateNextCoord(record) }
                        .forEach { crowPosition ->  crowProducer.produceMessages(gson.toJson(crowPosition)) }
                }
            }
        }
    }

    private suspend fun calculateNextCoord(record: ConsumerRecord<String, String>) {
//        val crowPosition = gson.fromJson(record.value(), CrowPosition::class.java)
//        if (coordinateCalculatorService.reached(crowPosition)) {
//            return
//        }
//
//        if (Duration.between(LocalDateTime.parse(record.key()), LocalDateTime.now()).seconds > 1) {
//            val nextLatLng = coordinateCalculatorService.calculateNextCoord(
//                crowPosition.idCrow,
//                crowPosition.latLng.lat,
//                crowPosition.latLng.lng)
//            println("Crow position - Lat: ${nextLatLng.lat} Lng: ${nextLatLng.lng}")
//            crowProducer.produceMessages(gson.toJson(CrowPosition(crowPosition.idCrow, nextLatLng)))
//        } else {
//            println("Crow position - Lat: ${crowPosition.latLng.lat} Lng: ${crowPosition.latLng.lng}")
//            crowProducer.produceMessages(gson.toJson(crowPosition))
//        }
    }

    private fun createConsumer(): KafkaConsumer<String, String> {
        val props = mapOf(
            "bootstrap.servers" to "pkc-ldjyd.southamerica-east1.gcp.confluent.cloud:9092",
            "security.protocol" to "SASL_SSL",
            "sasl.jaas.config" to "org.apache.kafka.common.security.plain.PlainLoginModule required username='$CLUSTER_API_KEY' password='$CLUSTER_API_SECRET';",
            "sasl.mechanism" to "PLAIN",
            "acks" to "all",
            "retries" to "0",
            "auto_offset_reset_config" to "earliest",
            "key.deserializer" to "org.apache.kafka.common.serialization.StringDeserializer",
            "value.deserializer" to "org.apache.kafka.common.serialization.StringDeserializer",
            MAX_POLL_RECORDS_CONFIG to "5",
            GROUP_ID_CONFIG to "crow_gps_group_1"
        )

        return KafkaConsumer(props)
    }
}
