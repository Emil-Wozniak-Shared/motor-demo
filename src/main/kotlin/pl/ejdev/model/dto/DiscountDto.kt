package pl.ejdev.model.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class DiscountDto(
    var code: String? = null,
    var name: String? = null,
    @Serializable(with = BigDecimalSerializer::class)
    var value: BigDecimal? = null,
    var position: Int = 0
)