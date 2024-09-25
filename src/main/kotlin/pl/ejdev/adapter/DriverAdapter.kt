package pl.ejdev.adapter

import io.higson.runtime.ext.adapter.Adapter
import io.higson.runtime.ext.adapter.Mapping
import io.higson.runtime.rhino.RhinoDate
import pl.ejdev.model.Driver
import java.util.*

class DriverAdapter(private val driver: Driver?) : Adapter() {
    override fun getMapping(): Mapping = Mapping()
        .add("firstname", driver?.firstName)
        .add("lastname", driver?.lastName)
        .add("dateofbirth", driver?.dateOfBirth)
        .add("gender", driver?.gender)
        .add("age", computeAge())
        .add("numberOfAccidents", driver?.numberOfAccidents)
        .add("numberOfTickets", driver?.numberOfTickets)
        .add("licenceObtainedAtAge", driver?.licenceObtainedAtAge)
        .add("address", driver?.address?.let(::AddressAdapter))

    private fun computeAge(): Int = RhinoDate.getAbsoluteYearDiff(driver?.dateOfBirth, Date())
}