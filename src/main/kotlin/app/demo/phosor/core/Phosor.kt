package app.demo.phosor.core

import java.io.File
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class Phosor(val verbose: Boolean) {

    fun process(workingDirectory: File, dateFormatter: SimpleDateFormat) {
        log("Phosor started at ${ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"))}, " +
                "workingDirectory: ${workingDirectory.absolutePath}, " +
                "pattern: ${dateFormatter.toPattern()}")

    }

    private fun log(msg: String) { if (verbose) { println(msg) } }
}