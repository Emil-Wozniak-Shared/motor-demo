package pl.ejdev.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import pl.ejdev.example.Motor
import pl.ejdev.model.Driver
import pl.ejdev.model.Gender
import pl.ejdev.model.ProblemDetails
import pl.ejdev.model.converter.QuoteConverter
import pl.ejdev.model.dto.DictionaryEntryDto
import pl.ejdev.plugins.Constants.RESOURCE_INDEX
import pl.ejdev.plugins.Constants.RESOURCE_STATIC
import pl.ejdev.plugins.Constants.STATIC_ROUTE
import pl.ejdev.service.DictionaryService
import pl.ejdev.service.DriverService
import pl.ejdev.service.QuoteService
import pl.ejdev.util.toDate
import pl.ejdev.util.toLocalDate

private object Constants {
    const val STATIC_ROUTE = "/static"
    const val RESOURCE_STATIC = "static/static"
    const val RESOURCE_INDEX = "static/index.html"
}

fun Application.configureRouting() {
    val driverService: DriverService by inject()
    val dictionaryService: DictionaryService by inject()
    val quoteService by inject<QuoteService>()
    val motor: Motor by inject()
    routing {
        get("/webjars") {
            call.respondText("<script src='/webjars/jquery/jquery.js'></script>", ContentType.Text.Html)
        }
        route("/quote") {
            get {
                quoteService.getQuote()
                    .also(motor::calculate)
                    .let(QuoteConverter::convert)
                    .also { call.application.environment.log.info("Quote found: $it") }
                    .let { call.respond(it) }
            }
        }
        route("/driver") {
            get { call.respond<Driver>(driverService.getDriver()) }
            put { handleUpdateDriver(driverService) }
        }
        route("/dictionaries") {
            get("/make") {
                val productionYear = call.queryParameters["productionYear"]!!.toInt()
                dictionaryService.getMakeDictionary(productionYear)
                    .map { DictionaryEntryDto(code = it.code, name = it.name) }
                    .let { call.respond(it) }
            }
            get("/model") {
                val typeId = call.queryParameters["typeId"]!!.toInt()
                dictionaryService.getModelDictionary(typeId)
                    .map { DictionaryEntryDto(code = it.code, name = it.name) }
                    .let { call.respond(it) }
            }
            get("/productionYear") {
                dictionaryService.getProductionYearDictionary()
                    .map { DictionaryEntryDto(code = it.code, name = it.name) }
                    .let { call.respond(it) }
            }
            get("/type") {
                val makeId = call.queryParameters["makeId"]!!.toInt()
                dictionaryService.getTypeDictionary(makeId)
                    .map { DictionaryEntryDto(code = it.code, name = it.name) }
                    .let { call.respond(it) }
            }
        }
        static {
            resource("/", RESOURCE_INDEX)
            static(STATIC_ROUTE) {
                resources(RESOURCE_STATIC)
            }
        }
    }
}

private suspend fun RoutingContext.handleUpdateDriver(driverService: DriverService) {
    var problem: ProblemDetails? = null
    call.parameters.forEach { param, values ->
        when (param) {
            "zipCode" -> driverService.updateZipCode(values[0])
            "city" -> driverService.updateCity(values[0])
            "street" -> driverService.updateStreet(values[0])
            "birthDate" -> driverService.updateBirthDate(values[0].toLocalDate().toDate())
            "firstName" -> driverService.updateFirstName(values[0])
            "lastName" -> driverService.updateLastName(values[0])
            "gender" -> driverService.updateGender(Gender.valueOf(values[0]))
            "accidentCount" -> driverService.updateAccidentCount(values[0].toInt())
            "trafficTicketsCount" -> driverService.updateTrafficTicketsCount(values[0].toInt())
            "licenceObtainedAtAge" -> driverService.licenceObtainedAtAge(values[0].toInt())
            else -> problem = ProblemDetails.from(
                call, "Unknown driver param", "param: $param"
            )
        }
    }
    return if (problem != null) call.respond(HttpStatusCode.BadRequest, problem!!)
    else call.respond(driverService.getDriver())
}


