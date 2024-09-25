package pl.ejdev.model.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class CoverageDto(
    var position: Int = 0,
    var code: String? = null,
    var name: String? = null,
    @Serializable(with = BigDecimalSerializer::class)
    var limitLeft: BigDecimal? = null,
    @Serializable(with = BigDecimalSerializer::class)
    var limitRight: BigDecimal? = null,
    @Serializable(with = BigDecimalSerializer::class)
    var premium: BigDecimal? = null,
    var description: String? = null,
)