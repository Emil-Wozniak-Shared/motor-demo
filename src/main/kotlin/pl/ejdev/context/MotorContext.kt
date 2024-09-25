package pl.ejdev.context

import io.higson.runtime.core.HigsonContext
import io.higson.runtime.ext.adapter.Adapter
import org.apache.commons.lang3.StringUtils.substringAfter
import org.apache.commons.lang3.StringUtils.substringBefore
import pl.ejdev.adapter.CoverageAdapter
import pl.ejdev.adapter.OptionAdapter
import pl.ejdev.adapter.QuoteAdapter
import pl.ejdev.model.Coverage
import pl.ejdev.model.Driver
import pl.ejdev.model.Option
import pl.ejdev.model.Quote

class MotorContext : HigsonContext {
    var quote: Quote? = null
        get() {
            if (field == null && coverage != null) {
                field = coverage?.quote
            }

            if (field == null && getOption() != null) {
                field = getOption()?.quote
            }

            return field
        }

    /*
	 * PART 1 :: dynamic context
	 */
    private var coverage: Coverage? = null
    var driver: Driver? = null
        get() {
            if (field == null && quote != null) {
                field = quote!!.driver
            }
            return field
        }
    private var option: Option? = null

    constructor(coverage: Coverage?) {
        this.coverage = coverage
    }

    constructor(option: Option?) {
        this.option = option
    }

    private fun getOption(): Option? {
        if (option == null && coverage != null) {
            option = coverage?.option
        }

        return option
    }


    /*
    * PART 2 :: canonical model adapter
    */
    /**
     * get value from path,
     * where path is valid path in canonical model,
     * for example:
     * - quote.insured.professionCode
     * - quote.insured.age
     * - quote.productCode
     * - cover.code
     */
    override fun get(path: String): Any? {
        val name = getFirstToken(path)
        val subpath = skipFirstToken(path)

        var adapter: Adapter? = null
        if (name == "quote") {
            adapter = QuoteAdapter(quote)
        }
        if (name == "coverage") {
            adapter = CoverageAdapter(coverage)
        }
        if (name == "option") {
            adapter = OptionAdapter(getOption())
        }
        if (adapter != null) {
            return adapter.get(subpath)
        }
        return null
    }

    private fun getFirstToken(path: String): String = substringBefore(path, ".")

    private fun skipFirstToken(path: String): String = substringAfter(path, ".")
}