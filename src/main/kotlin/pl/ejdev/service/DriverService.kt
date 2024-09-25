package pl.ejdev.service

import pl.ejdev.model.Driver
import pl.ejdev.model.Gender
import java.util.*

internal class DriverService(
    private val quoteService: QuoteService
) {

    fun getDriver(): Driver = quoteService.getQuote().driver

    fun updateFirstName(firstName: String?) {
        getDriver().also { it.firstName = firstName }
    }

    fun updateGender(gender: Gender) {
        getDriver().also { it.gender = gender }
    }

    fun updateLastName(lastName: String?) {
        getDriver().also { it.lastName = lastName }
    }

    fun updateBirthDate(birthDate: Date) {
        getDriver().also { it.dateOfBirth = birthDate }
    }

    fun updateZipCode(zipCode: String) {
        getDriver().address?.also { it.zipCode = zipCode }
    }

    fun updateCity(city: String) {
        getDriver().address?.also { it.city = city }
    }

    fun updateStreet(street: String) {
        getDriver().address?.also { it.street = street }
    }

    fun updateAccidentCount(accidentCount: Int) {
        getDriver().also { it.numberOfAccidents = accidentCount }
    }

    fun updateTrafficTicketsCount(trafficTicketsCount: Int) {
        getDriver().also { it.numberOfTickets =  trafficTicketsCount }
    }

    fun licenceObtainedAtAge(licenceObtainedAtAge: Int) {
        getDriver().also { it.licenceObtainedAtAge = licenceObtainedAtAge }
    }
}