package pl.ejdev.example

import io.higson.runtime.core.HigsonContext
import io.higson.runtime.core.HigsonEngine
import io.higson.runtime.engine.core.function.FunctionInvocationException
import io.higson.runtime.exception.HigsonRuntimeException
import io.higson.runtime.model.DomainObject
import pl.ejdev.context.MotorContext
import pl.ejdev.exception.FunctionProblem
import pl.ejdev.model.*
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import java.util.Comparator.comparingInt

internal class Motor(
    private val engine: HigsonEngine
) {

    fun createQuote(): Quote {
        // basic configuration:
        //  1. plan
        //  2. options defined for this plan
        //  3. coverages defined for this plan

        // 1. obtain FULL plan handle
        val plan = engine.getDomain("DEMO", "/PLANS[FULL]")

        // 2. all rating options
        val options = plan.getChildren("OPTIONS")

        // 3. all coverages for this plan
        val coverages = plan.getChildren("COVERAGES")

        // 4. create quote
        val quote = buildQuote(plan, options, coverages)
        return quote
    }

    fun calculate(quote: Quote) {
        QuoteValidator.validateQuote(quote)
        val planCode = quote.plan

        // handle to plan
        val plan = engine.getDomain("DEMO", "/").getChild("PLANS", planCode)

        // for each option and each coverage
        quote.options.forEach { option ->
            rebuildCoverages(plan.getChildren("COVERAGES"), option, quote.vehicle)
            // calculate premium for each coverage
            option.coverages.forEach { coverage ->
                // coverage definition handle

                val coverDef = plan.getChild("COVERAGES", coverage.code)

                // dynamic context - derives all paths from current coverage
                val ctx = MotorContext(coverage)

                // get LIMIT_1 and LIMIT_2 attributes
                val limit1 = coverDef.getAttrDecimal("LIMIT_1", ctx)
                val limit2 = coverDef.getAttrDecimal("LIMIT_2", ctx)

                coverage.limit1 = limit1
                coverage.limit2 = limit2

                // get PREMIUM attribute
                try {
                    val premium = coverDef.getAttr("PREMIUM").getDecimal(ctx)
                    coverage.premium = premium
                } catch (ex: HigsonRuntimeException) {
                    throw Exception(ex.message, ex)
                } catch (ex: FunctionInvocationException) {
                    throw FunctionProblem(ex.message, ex.cause?.message)
                }
            }

            val ctx = MotorContext(option)

            // check discounts
            option.clearDiscounts()
            plan.getChildren("DISCOUNTS").forEach { discount ->
                if (discount.getAttrBoolean("available", ctx)) {
                    val value = discount.getAttr("value").getDecimal(ctx)
                    val position = discount.getAttr("position").intValue(ctx)
                    option.addDiscount(Discount(discount.code, discount.name, value, position))
                }
            }

            option.discounts.sortWith(comparingInt(Discount::position))
        }

    }

    /**
     * @param plan      rating plan
     * @param options   all options for this plan
     * @param coverages all coverages fot this plan
     * @return constructed quote with coverages for all options
     */
    private fun buildQuote(plan: DomainObject, options: List<DomainObject>, coverages: List<DomainObject>): Quote {
        // sample driver's data

        val adr: Address = createAddress()
        val driver: Driver = createDriver(adr)
        val vehicle = createVehicle()
        val quote = Quote(plan.code, driver)
        quote.vehicle = vehicle

        options.forEach {
            val option = Option(it.code, it.getAttr("ORDER").intValue(HigsonContext()))
            quote.addOption(option)
            rebuildCoverages(coverages, option, vehicle)
        }
        return quote
    }

    private fun createVehicle(): Vehicle = Vehicle(
        productionYear = 2010,
        makeId = 217,
        make = "TOYOTA",
        typeId = 28654,
        modelId = 218915
    )

    private fun rebuildCoverages(coverages: List<DomainObject>, option: Option, vehicle: Vehicle) {
        for (c in coverages) {
            val ctx: HigsonContext = getHigsonContext(option, vehicle, c)
            // get IS_AVAILABLE attribute's value
            val isAvailable = c.getAttrBoolean("IS_AVAILABLE", ctx)

            val optionCoverage: Optional<Coverage> = option.coverages
                .stream()
                .filter { cov -> cov.code == c.code }
                .findFirst()

            // add/update coverage only if available for this option
            if (isAvailable) {
                if (optionCoverage.isPresent) {
                    // update
                    setCoverData(c, optionCoverage.get(), ctx)
                } else {
                    // add new
                    val cover = Coverage(c.code)
                    setCoverData(c, cover, ctx)
                    option.addCoverage(cover)
                }
            }
            // remove not available existing coverage
            if (!isAvailable && optionCoverage.isPresent) {
                option.coverages.remove(optionCoverage.get())
            }
        }

        // sort coverages according to position attribute
        option.coverages.sortWith(comparingInt(Coverage::position))
    }

    private fun getHigsonContext(option: Option, vehicle: Vehicle, coverage: DomainObject): HigsonContext =
        HigsonContext(
            "option.code", option.code,
            "coverage.code", coverage.code,
            "vehicle.make", vehicle.make,
            "vehicle.makeId", vehicle.makeId,
            "option.quote.address.zipCode", option.quote?.driver?.address?.zipCode
        )

    private fun setCoverData(domainDataCover: DomainObject, cover: Coverage, ctx: HigsonContext) {
        cover.name = domainDataCover.name
        cover.position = domainDataCover.getAttr("POSITION").intValue(ctx)
        val description = domainDataCover.getAttr("DESCRIPTION")
        cover.description = description?.getString(ctx)
    }

    private fun createAddress(): Address = Address(
        city = "Lake Jackson",
        street = "Allwood St",
        zipCode = "77566"
    )

    private fun createDriver(address: Address): Driver = Driver(
        firstName = "John",
        lastName = "Doe",
        gender = Gender.M,
        dateOfBirth = Date.from(
            LocalDate.now()
                .minusYears(40)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
        ),
        licenceObtainedAtAge = 18,
        address = address
    )
}