package pl.ejdev.model

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    var city: String,
    var street: String,
    var zipCode: String
)