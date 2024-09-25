package pl.ejdev.model

import kotlinx.serialization.Serializable

@Serializable
data class Vehicle(
    var make: String? = null,
    var makeId: Int = 0,
    var typeId: Int = 0,
    var modelId: Int = 0,
    var productionYear: Int = 0
)