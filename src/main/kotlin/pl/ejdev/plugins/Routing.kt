package pl.ejdev.plugins

import com.fasterxml.jackson.databind.SerializationFeature
import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.serialization.jackson.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.webjars.*
import kotlinx.serialization.Serializable
import pl.ejdev.plugins.Constants.RESOURCE_INDEX
import pl.ejdev.plugins.Constants.RESOURCE_STATIC
import pl.ejdev.plugins.Constants.STATIC_ROUTE

object Constants {
    const val STATIC_ROUTE = "/static"
    const val RESOURCE_STATIC = "static/static"
    const val RESOURCE_INDEX = "static/index.html"
}

fun Application.configureRouting() {
    install(Webjars) { path = "/webjars" /*defaults to /webjars */ }
    install(ContentNegotiation) {
        jackson { enable(SerializationFeature.INDENT_OUTPUT) }
        json()
    }
    install(SwaggerUI) {
        swagger {
            swaggerUrl = "swagger-ui"
            forwardRoot = true
        }
        info {
            title = "Example API"
            version = "latest"
            description = "Example API for testing and demonstration purposes."
        }
        server {
            url = "http://localhost:8080"
            description = "Development Server"
        }
    }
    install(Resources)
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        get("/webjars") {
            call.respondText("<script src='/webjars/jquery/jquery.js'></script>", ContentType.Text.Html)
        }
        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }
        get("/json/kotlinx-serialization") {
            call.respond(mapOf("hello" to "world"))
        }
        get<Articles> { article ->
            call.respond("List of articles sorted starting from ${article.sort}")
        }
        static {
            resource("/", RESOURCE_INDEX)
            static(STATIC_ROUTE) { resources(RESOURCE_STATIC) }
        }
    }
}

@Serializable
@Resource("/articles")
class Articles(val sort: String? = "new")
