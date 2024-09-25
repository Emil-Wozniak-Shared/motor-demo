package pl.ejdev.model

import com.fasterxml.jackson.annotation.JsonFormat
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import pl.ejdev.model.dto.BigDecimalSerializer
import java.util.*

@Serializable
data class Driver(
    var firstName: String? = null,
    var lastName: String? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Contextual
    var dateOfBirth: Date? = null,
    var gender: Gender? = null,
    var address: Address? = null,
    // number of accidents in the last 5 years
    var numberOfAccidents: Int = 0,
    // number of traffic tickets in the last 5 years
    var numberOfTickets: Int = 0,
    // driver's age when driver licence was obtained
    var licenceObtainedAtAge: Int = 0
)
