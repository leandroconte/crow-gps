package br.com.leandroconte.anticorruption.kafka.producer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.koin.core.annotation.Single
import java.time.LocalDateTime

private const val CLUSTER_API_KEY = "XR5RXAGTNZIIRWM6"
private const val CLUSTER_API_SECRET = "4Tzc8pTpBKEgrdeqzO3fFh/x95Q3DI9/y9edsLjRNXnwS06LhvbrsAKZ7VxI2CLY"

@Single
class CrowProducer {

    val producer = createProducer()

    private fun createProducer(): Producer<String, String> {
        val props = mapOf(
            "bootstrap.servers" to "pkc-ldjyd.southamerica-east1.gcp.confluent.cloud:9092",
            "security.protocol" to "SASL_SSL",
            "sasl.jaas.config" to "org.apache.kafka.common.security.plain.PlainLoginModule required username='$CLUSTER_API_KEY' password='$CLUSTER_API_SECRET';",
            "sasl.mechanism" to "PLAIN",
            "acks" to "all",
            "retries" to "0",
            "linger.ms" to "1",
            "key.serializer" to "org.apache.kafka.common.serialization.StringSerializer",
            "value.serializer" to "org.apache.kafka.common.serialization.StringSerializer"
        )

        return KafkaProducer(props)
    }

    fun produceMessages(message: String) {
        val time = LocalDateTime.now()
        val messageSent = ProducerRecord(
            "topic_crows_gps",
            time.toString(), // key
            message
        )

        producer.send(messageSent) { m: RecordMetadata, e: Exception? ->
            when (e) {
                // no exception, good to go!
                null -> println("Produced record to topic ${m.topic()} partition [${m.partition()}] @ offset ${m.offset()}")
                // print stacktrace in case of exception
                else -> e.printStackTrace()
            }
        }
    }
}