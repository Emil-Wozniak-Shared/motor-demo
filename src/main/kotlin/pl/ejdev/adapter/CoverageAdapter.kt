package pl.ejdev.adapter

import io.higson.runtime.ext.adapter.Adapter
import io.higson.runtime.ext.adapter.Mapping
import pl.ejdev.model.Coverage

class CoverageAdapter(private val coverage: Coverage?) : Adapter() {
    override fun getMapping(): Mapping = Mapping() // simple types (model leaves)
        .add("code", coverage?.code)
        .add("limit1", coverage?.limit1)
        .add("limit2", coverage?.limit2)
        .add("premium", coverage?.premium) // parent option (there is exactly one parent option)
        .add("option", OptionAdapter(coverage?.option)) // parent quote (there is exactly one parent quote)
        .add("quote", QuoteAdapter(coverage?.quote))
}