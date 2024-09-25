package pl.ejdev.service

import io.higson.runtime.core.HigsonContext
import io.higson.runtime.core.HigsonEngine
import io.higson.runtime.engine.core.context.ParamContext
import io.higson.runtime.engine.core.output.MultiValue
import io.higson.runtime.engine.core.output.ParamValue

class DictionaryService(
    private val engine: HigsonEngine
) {
    fun getMakeDictionary(productionYear: Int): List<DictionaryEntry> = getHigsonDictionary(
        Dictionaries.VEHICLE_MAKES,
        HigsonContext("quote.vehicle.productionYear", productionYear),
        this::vehicleMakeRowToDictionaryEntry
    )

    fun getModelDictionary(typeId: Int?): List<DictionaryEntry> = getHigsonDictionary(
        Dictionaries.VEHICLE_MODEL,
        HigsonContext("quote.vehicle.typeId", typeId),
        this::vehicleModelRowToDictionaryEntry
    )

    fun getProductionYearDictionary(): List<DictionaryEntry> = getHigsonDictionary(
        Dictionaries.PRODUCTION_YEAR,
        HigsonContext(),
        this::rowToSimpleEntry
    )

    fun getTypeDictionary(makeId: Int?): List<DictionaryEntry> =
        getHigsonDictionary(
            Dictionaries.VEHICLE_TYPE,
            HigsonContext("quote.vehicle.makeId", makeId),
            this::vehicleTypeRowToDictionaryEntry
        )

    private fun <T> getHigsonDictionary(
        parameter: String,
        context: ParamContext,
        mapFunction: (MultiValue) -> T
    ): List<T> {
        val paramValue: ParamValue = engine[parameter, context]
        return getResultFromParamValue(paramValue, mapFunction)
    }

    private fun <T> getResultFromParamValue(paramValue: ParamValue, mapFunction: (MultiValue) -> T): List<T> {
        return paramValue.rows().map(mapFunction)
    }

    private fun vehicleMakeRowToDictionaryEntry(r: MultiValue): DictionaryEntry = DictionaryEntry(
        code = r.getString("make_id"),
        name = r.getString("make"),
    )

    private fun vehicleModelRowToDictionaryEntry(r: MultiValue): DictionaryEntry = DictionaryEntry(
        code = r.getString("model_id"),
        name = r.getString("model_label"),
    )

    private fun vehicleTypeRowToDictionaryEntry(r: MultiValue): DictionaryEntry = DictionaryEntry(
        code = r.getString("type_id"),
        name = r.getString("type"),
    )

    private fun rowToSimpleEntry(r: MultiValue): DictionaryEntry = DictionaryEntry(
        code = r.getString("code"),
        name = r.getString("name"),
    )
}