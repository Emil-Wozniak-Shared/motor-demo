package pl.ejdev.service

import pl.ejdev.example.Motor
import pl.ejdev.model.Quote
import javax.annotation.PostConstruct

internal class QuoteService(
    private val motor: Motor
) {
    private lateinit var sessionQuote: Quote

    init {
        sessionQuote = motor.createQuote()
    }

    fun getQuote(): Quote = sessionQuote
}