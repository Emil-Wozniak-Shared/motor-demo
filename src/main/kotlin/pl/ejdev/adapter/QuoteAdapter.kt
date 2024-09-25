package pl.ejdev.adapter

import io.higson.runtime.ext.adapter.Adapter
import io.higson.runtime.ext.adapter.CollectionAdapter
import io.higson.runtime.ext.adapter.Mapping
import pl.ejdev.model.Quote

class QuoteAdapter(private val quote: Quote?) : Adapter() {
    override fun getMapping(): Mapping = Mapping()
        .add("planCode", quote?.plan)
        .add("driver", DriverAdapter(quote?.driver))
        .add("vehicle", VehicleAdapter(quote?.vehicle))
        .add("options", CollectionAdapter(quote?.options, ::OptionAdapter))
}