package pl.ejdev.plugins

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.webjars.*
import pl.ejdev.model.Driver
import pl.ejdev.model.Gender
import pl.ejdev.model.ProblemDetails
import pl.ejdev.plugins.Constants.RESOURCE_INDEX
import pl.ejdev.plugins.Constants.RESOURCE_STATIC
import pl.ejdev.plugins.Constants.STATIC_ROUTE
import java.time.LocalDate

object Constants {
    const val STATIC_ROUTE = "/static"
    const val RESOURCE_STATIC = "static/static"
    const val RESOURCE_INDEX = "static/index.html"
}

fun Application.configureRouting() {
    install(CORS) {
        anyHost()
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)
    }
    install(Webjars) { path = "/webjars" /*defaults to /webjars */ }
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            enable(SerializationFeature.WRITE_DATES_WITH_CONTEXT_TIME_ZONE)
            registerModules(JavaTimeModule())
        }
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
    val message = Driver.Default
    routing {
        get("/webjars") {
            call.respondText("<script src='/webjars/jquery/jquery.js'></script>", ContentType.Text.Html)
        }
        route("/driver") {
            get { call.respond<Driver>(message) }
            put { call.respond(handleUpdateDriver(message)) }
        }
        static {
            resource("/", RESOURCE_INDEX)
            static(STATIC_ROUTE) {
                resources(RESOURCE_STATIC)
            }
        }
    }
}

private suspend fun RoutingContext.handleUpdateDriver(driver: Driver): Driver = driver.apply {
    var problem: ProblemDetails? = null
    call.parameters.forEach { param, values ->
        when (param) {
            "zipCode" -> zipCode = values[0]
            "city" -> city = values[0]
            "street" -> street = values[0]
            "birthDate" -> birthDate =
                values[0].split("-").map(String::toInt).let { (y, m, d) -> LocalDate.of(y, m, d) }

            "firstName" -> firstName = values[0]
            "lastName" -> lastName = values[0]
            "gender" -> gender = Gender.valueOf(values[0])
            "accidentCount" -> accidentCount = values[0].toInt()
            "trafficTicketsCount" -> trafficTicketsCount = values[0].toInt()
            "licenceObtainedAtAge" -> licenceObtainedAtAge = values[0].toInt()
            else -> problem = ProblemDetails.from(
                call,
                "Unknown driver param", "param: $param"
            )
        }
    }
    if (problem != null) call.respond(HttpStatusCode.BadRequest, problem!!)
    else call.respond(driver)
}


