package pl.ejdev.model.converter

import pl.ejdev.model.Coverage
import pl.ejdev.model.Discount
import pl.ejdev.model.Option
import pl.ejdev.model.Quote
import pl.ejdev.model.dto.CoverageDto
import pl.ejdev.model.dto.DiscountDto
import pl.ejdev.model.dto.OptionDto
import pl.ejdev.model.dto.QuoteDto
import java.math.BigDecimal
import java.util.stream.Collectors

object QuoteConverter {
    fun convert(quote: Quote): QuoteDto = QuoteDto(
        options =  quote.options
            .map { option: Option -> this.toOptionDto(option) }
    )

    private fun toOptionDto(option: Option): OptionDto {
        val dto = OptionDto()
        dto.code = option.code
        dto.coverages = convertCoverages(option.coverages)
        dto.discounts = convertDiscounts(option.discounts)
        dto.order = option.order ?: 0
        val optionPremium = getOptionPremium(option)
        dto.premium = optionPremium
        dto.premiumBeforeDiscounts = optionPremium
        updateOptionWithDiscounts(dto)
        return dto
    }

    private fun convertCoverages(coverages: List<Coverage>): List<CoverageDto> {
        return coverages
            .stream()
            .map { coverage: Coverage -> this.toCoverageDto(coverage) }
            .collect(Collectors.toList())
    }

    private fun convertDiscounts(discounts: List<Discount>): List<DiscountDto> {
        return discounts
            .stream()
            .map { discount: Discount -> this.toDiscountDto(discount) }
            .collect(Collectors.toList())
    }

    private fun toCoverageDto(coverage: Coverage): CoverageDto {
        val dto = CoverageDto()
        dto.code = coverage.code
        dto.name = coverage.name
        dto.description = coverage.description
        dto.limitLeft = coverage.limit1
        dto.limitRight = coverage.limit2
        dto.premium = coverage.premium
        dto.position = coverage.position
        return dto
    }

    private fun toDiscountDto(discount: Discount): DiscountDto {
        val dto = DiscountDto()
        dto.name = discount.name
        dto.code = discount.code
        dto.value = discount.value
        dto.position = discount.position
        return dto
    }

    private fun getOptionPremium(option: Option): BigDecimal? {
        return option.coverages
            .stream()
            .map(Coverage::premium)
            .reduce { a, b -> a?.add(b) }
            .orElse(BigDecimal.ZERO)
    }

    private fun updateOptionWithDiscounts(option: OptionDto) {
        option.discounts?.forEach { (_, _, value) ->
            val oldOptionPremium = option.premium
            option.premium = oldOptionPremium!!.subtract(value)
        }
    }
}