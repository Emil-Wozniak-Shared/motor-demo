package pl.ejdev.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class QuoteDto(
    var  options: List<OptionDto> = emptyList(),
)
