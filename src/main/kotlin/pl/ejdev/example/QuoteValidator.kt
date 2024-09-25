package pl.ejdev.example

import pl.ejdev.exception.InvalidCarDataException
import pl.ejdev.exception.InvalidDriverDataException
import pl.ejdev.model.Driver
import pl.ejdev.model.Quote
import pl.ejdev.model.Vehicle

object QuoteValidator {

    fun validateQuote(quote: Quote) {
        validateDriver(quote.driver)
        validateVehicle(quote.vehicle)
    }

    private fun validateDriver(driver: Driver) {
        checkConditionAndThrowInvalidDriverDataException(driver.dateOfBirth == null, "Driver birth date not supplied.")
        checkConditionAndThrowInvalidDriverDataException(driver.firstName == null, "Driver first name not supplied.")
        checkConditionAndThrowInvalidDriverDataException(driver.gender == null, "Driver gender not supplied.")
        checkConditionAndThrowInvalidDriverDataException(driver.address == null, "Driver address not supplied.")
        checkConditionAndThrowInvalidDriverDataException(
            driver.address?.zipCode == null,
            "Driver address zip code not supplied."
        )
        checkConditionAndThrowInvalidDriverDataException(
            driver.address?.city== null,
            "Driver address city not supplied."
        )
        checkConditionAndThrowInvalidDriverDataException(
            driver.address?.street == null,
            "Driver address street not supplied."
        )
    }

    private fun checkConditionAndThrowInvalidDriverDataException(condition: Boolean, message: String) {
        if (condition) {
            throw InvalidDriverDataException(message)
        }
    }

    private fun validateVehicle(vehicle: Vehicle) {
        checkConditionAndThrowInvalidCarDataException(
            vehicle.productionYear == 0,
            "Vehicle production year not supplied."
        )
        checkConditionAndThrowInvalidCarDataException(vehicle.makeId == 0, "Vehicle make not supplied.")
        checkConditionAndThrowInvalidCarDataException(vehicle.typeId == 0, "Vehicle type not supplied.")
        checkConditionAndThrowInvalidCarDataException(vehicle.modelId == 0, "Vehicle model not supplied.")
    }

    private fun checkConditionAndThrowInvalidCarDataException(condition: Boolean, message: String) {
        if (condition) {
            throw InvalidCarDataException(message)
        }
    }
}