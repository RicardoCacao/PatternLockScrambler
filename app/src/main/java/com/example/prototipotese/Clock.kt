package com.example.prototipotese

import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object Clock {
    fun isHourEven(
        hora: String = DateTimeFormatter
            .ofPattern("HH:mm")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())
    ): Boolean {

        return hora.substringBefore(":").toInt() % 2 == 0
    }

    fun isMinuteEven(
        hora: String = DateTimeFormatter
            .ofPattern("HH:mm")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())
    ): Boolean {
        return hora.substringAfter(":").toInt() % 2 == 0
    }
}