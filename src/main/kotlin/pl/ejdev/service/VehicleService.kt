package pl.ejdev.service

import pl.ejdev.model.Vehicle

internal class VehicleService(
    private val quoteService: QuoteService
) {

    fun getVehicle(): Vehicle = quoteService.getQuote().vehicle
}