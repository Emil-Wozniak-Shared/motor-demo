package pl.ejdev.adapter

import io.higson.runtime.ext.adapter.Adapter
import io.higson.runtime.ext.adapter.Mapping
import pl.ejdev.model.Address

class AddressAdapter(private val address: Address) : Adapter() {
    override fun getMapping(): Mapping = Mapping()
        .add("city", address.city)
        .add("street", address.street)
        .add("zipcode", address.zipCode)
}