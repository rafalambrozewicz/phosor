package app.demo.phosor.core

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

object CliArgsDesc {

    fun catalogNameFormat(defaultFormat: String, allowedCharacters: CharArray)
            = "Date pattern used to name catalogs, by default equlas to '$defaultFormat' that would create catalog " +
            "named like " +
            "'${SimpleDateFormat(defaultFormat).apply { timeZone = TimeZone.getDefault() }.format(Date.from(Instant.now()))}' " +
            "for photo taken today. Pattern can contain only the following characters: " +
            "${allowedCharacters.joinToString(", "){ "'${it}'" }}, where: " +
            """
                'G'	Era designator	(ex. AD),
                'y'	Year	(ex. 1996; 96),
                'Y'	Week year   (ex. 2009; 09),
                'M'	Month in year	(ex. July; Jul; 07),
                'w'	Week in year	(ex. 27),
                'W'	Week in month	(ex. 2),
                'D'	Day in year (ex. 189),
                'd'	Day in month	(ex. 10),
                'F'	Day of week in month	(ex. 2
                'E'	Day name in week	(ex. Tuesday; Tue),
                'u'	Day number of week (1 = Monday, ..., 7 = Sunday)
            """.trimIndent()

    fun verbose()
            = "Enable verbose output\n";

    fun workingDirectory()
            = "Working directory, current directory by default";
}