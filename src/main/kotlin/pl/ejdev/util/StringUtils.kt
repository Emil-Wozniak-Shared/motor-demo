package pl.ejdev.util

import java.time.LocalDate
import java.time.ZoneId
import java.util.*

fun String.toLocalDate(): LocalDate = this.split("-")
    .map(String::toInt)
    .let { (y, m, d) -> LocalDate.of(y, m, d) }

fun LocalDate.toDate(): Date = Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())