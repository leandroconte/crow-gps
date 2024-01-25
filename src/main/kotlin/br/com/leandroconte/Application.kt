package br.com.leandroconte

import br.com.leandroconte.plugins.*
import br.com.leandroconte.repository.DatabaseSingleton
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
//    configureDatabases()
    DatabaseSingleton.init()
    configureAdministration()
    configureHTTP()
    configureRouting()
    configureKoin()
}
