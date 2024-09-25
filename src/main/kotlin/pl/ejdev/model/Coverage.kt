package pl.ejdev.model

import java.math.BigDecimal

data class Coverage(
    val code: String,
    var name: String? = null,
    var position: Int = 0,
    var limit1: BigDecimal? = null,
    var limit2: BigDecimal? = null,
    var premium: BigDecimal? = null,
    var description: String? = null,
    var option: Option? = null
) {
    val quote: Quote?
        get() = option?.quote

    fun print(): String {
        val limit1str = if (limit1 != null) limit1?.toPlainString() else "-"
        val limit2str = if (limit2 != null) limit2?.toPlainString() else "-"

        return String.format(
            "    coverage [%5s]  ::  limits = %6s /%6s    premium = %7s",
            code, limit1str, limit2str, premium
        )
    }
}