package pl.ejdev.model

data class Quote(
    var plan: String? = null,
    var driver: Driver = Driver(),
    var vehicle: Vehicle = Vehicle(),
    val options: MutableList<Option> = mutableListOf()
) {

    fun addOption(option: Option) {
        option.quote = this
        options.add(option)
    }

    fun print(): String {
        val sb = StringBuilder()
        line(sb, "===========  QUOTE  begin ===========")

        options.forEach { option ->
            line(sb, "- option  [" + option.code + "] ")
            option.coverages.forEach { coverage -> line(sb, coverage.print()) }
            option.discounts.forEach { discount -> line(sb, discount.print()) }
        }

        line(sb, "===========  QUOTE  end   ===========")
        return sb.toString()
    }

    companion object {
        private fun line(sb: StringBuilder, str: String) {
            sb.append(str).append(System.lineSeparator())
        }
    }
}