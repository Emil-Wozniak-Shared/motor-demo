package pl.ejdev

import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import pl.ejdev.plugins.*

fun main(args: Array<String>) {
    io.ktor.server.cio.EngineMain.main(args)
}

fun Application.module() {
    configureHTTP()
    configureMonitoring()
//    configureDatabases()
    configureDependencyInjection()
    configureWeb()
    configureRouting()
}
