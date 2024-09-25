package pl.ejdev.model

class Option(
    val code: String?,
    val order: Int?,
    val coverages: MutableList<Coverage> = mutableListOf(),
    val discounts: MutableList<Discount> = mutableListOf(),
    var quote: Quote? = null
) {
    fun addCoverage(coverage: Coverage) {
        coverage.option = this
        coverages.add(coverage)
    }

    fun addDiscount(discount: Discount) = discounts.add(discount)
    fun clearDiscounts() = discounts.clear()
}