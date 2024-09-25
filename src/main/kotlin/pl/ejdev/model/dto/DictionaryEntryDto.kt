package pl.ejdev.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class DictionaryEntryDto(
    var code: String? = null,
    var name: String? = null
)
