package pl.ejdev.adapter

import io.higson.runtime.ext.adapter.Adapter
import io.higson.runtime.ext.adapter.CollectionAdapter
import io.higson.runtime.ext.adapter.Mapping
import pl.ejdev.model.Option

class OptionAdapter(private val option: Option?) : Adapter() {
    public override fun getMapping(): Mapping = Mapping()
        .add("code", option?.code)
        .add("coverages", CollectionAdapter(option?.coverages, CoverageAdapter::class.java))
        .add("quote", QuoteAdapter(option?.quote))
}