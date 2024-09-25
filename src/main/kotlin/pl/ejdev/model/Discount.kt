package pl.ejdev.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import pl.ejdev.model.dto.BigDecimalSerializer
import java.math.BigDecimal

@Serializable
data class Discount(
    val code: String,
    val name: String, // amount value
    @Serializable(with = BigDecimalSerializer::class)
    val value: BigDecimal,
    val position: Int
) {
    fun print(): String = String.format("    discount [%10s] =  %7s   (%s)", code, value, name)
}