package pl.ejdev.adapter

import io.higson.runtime.ext.adapter.Adapter
import io.higson.runtime.ext.adapter.Mapping
import pl.ejdev.model.Vehicle

class VehicleAdapter(private val vehicle: Vehicle?) : Adapter() {
    override fun getMapping(): Mapping = Mapping()
        .add("makeId", vehicle?.makeId)
        .add("make", vehicle?.make)
        .add("typeId", vehicle?.typeId)
        .add("modelId", vehicle?.modelId)
        .add("productionYear", vehicle?.productionYear)
}