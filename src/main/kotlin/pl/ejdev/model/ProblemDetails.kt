package pl.ejdev.model

import io.ktor.server.plugins.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ProblemDetails(
    val type: String, // "https://example.com/probs/out-of-credit",
    val title: String, //"You do not have enough credit.",
    val detail: String?, //"Your current balance is 30, but that costs 50.",
    val instance: String, // "/account/12345/msgs/abc",
) {
    companion object {
        fun from(
            call: RoutingCall,
            title: String,
            detail: String?,
        ): ProblemDetails = ProblemDetails(
            type = "${call.request.origin.scheme}://${call.request.headers["host"]}${call.request.origin.uri}",
            title = title,
            detail = detail,
            instance = call.request.origin.uri
        )
    }
}
