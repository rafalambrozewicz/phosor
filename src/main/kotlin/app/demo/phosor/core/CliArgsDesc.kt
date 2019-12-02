package app.demo.phosor.core

object CliArgsDesc {

    val catalogNameFormat
            = "Date pattern used to name catalogs, by default equals to '${ArgsParser.DEFAULT_CATALOG_NAME_FORMAT}' " +
            "where 'yyyy' is year (ex. 2019), 'MM' is month in year (ex. 12), 'dd' is day in month (ex. 02). " +
            "Pattern can contain only the following characters: " +
            "${ArgsParser.ALLOWED_PATTERN_CHARACTERS.joinToString(", "){ "'${it}'" }}, where: " +
            """
                'G'	is Era designator	(ex. AD),
                'y'	is Year	(ex. 1996; 96),
                'Y'	is Week year   (ex. 2009; 09),
                'M'	is Month in year	(ex. July; Jul; 07),
                'w'	is Week in year	(ex. 27),
                'W'	is Week in month	(ex. 2),
                'D'	is Day in year (ex. 189),
                'd'	is Day in month	(ex. 10),
                'F'	is Day of week in month	(ex. 2),
                'E'	is Day name in week	(ex. Tuesday; Tue),
                'u'	is Day number of week (1 = Monday, ..., 7 = Sunday)
            """.trimIndent()

    const val verbose = "Enable verbose output";

    const val workingDirectory = "Working directory, current directory by default";
}