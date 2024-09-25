package pl.ejdev.model.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.math.BigDecimal

@Serializable
data class OptionDto(
    var code: String? = null,
    var coverages: List<CoverageDto>? = null,
    var discounts: List<DiscountDto>? = null,
    var order: Int = 0,
    @Serializable(with = BigDecimalSerializer::class)
    var premium: BigDecimal? = null,
    @Serializable(with = BigDecimalSerializer::class)
    var premiumBeforeDiscounts: BigDecimal? = null

)

object BigDecimalSerializer: KSerializer<BigDecimal> {
    override fun deserialize(decoder: Decoder): BigDecimal {
        return decoder.decodeString().toBigDecimal()
    }

    override fun serialize(encoder: Encoder, value: BigDecimal) {
        encoder.encodeString(value.toPlainString())
    }

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("BigDecimal", PrimitiveKind.STRING)
}