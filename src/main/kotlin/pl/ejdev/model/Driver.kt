package pl.ejdev.model

import com.fasterxml.jackson.annotation.JsonFormat
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate


@Serializable
enum class Gender {
    MALE, FEMALE;
}

@Serializable
data class Driver(
    var zipCode: String,
    var city: String,
    var street: String,
    @Contextual
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    var birthDate: LocalDate,
    var firstName: String,
    var lastName: String,
    var gender: Gender,
    var accidentCount: Int,
    var trafficTicketsCount: Int,
    var licenceObtainedAtAge: Int
) {
    companion object  {
        val Default = Driver(
            zipCode = "00780",
            city = "Warsaw",
            street = "Złota",
            birthDate = LocalDate.of(2019, 12, 25),
            firstName = "John",
            lastName = "Snow",
            gender = Gender.MALE,
            accidentCount = 0,
            trafficTicketsCount = 0,
            licenceObtainedAtAge = 0
        )
    }
}
