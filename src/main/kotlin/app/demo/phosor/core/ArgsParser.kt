package app.demo.phosor.core

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.file
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ArgsParser(private val thenDo: (simpleDateFormat: SimpleDateFormat,
                                      directory: File,
                                      verbose: Boolean) -> Unit) : CliktCommand(
        name= "phosor",
        help = """
            Phosor is a command line tool that groups photos by creation date.
            
            For each photo in given directory its creation date gets determined, catalog with creation date created, and photo moved to it.
        """.trimIndent()) {
    init {
        versionOption("0.1")
    }

    companion object {
        val ALLOWED_PATTERN_CHARACTERS = "-_.[]GyYMwWDdFEu".toCharArray()
        const val DEFAULT_CATALOG_NAME_FORMAT = "[YYYY-MM-dd]";

        object Error {
            fun patternEmpty() = "--pattern cannot be empty"
            fun patternHasIllegalCharacter(c: Char) = "--pattern has illegal character '$c'"
            fun patternParsingError(errorMsg: String?) = "--pattern parsing failed, details: '${errorMsg ?: "No details"}'"
        }
    }

    private val pattern: String by option(
            "-p", "--pattern",
            help = CliArgsDesc.catalogNameFormat(DEFAULT_CATALOG_NAME_FORMAT, ALLOWED_PATTERN_CHARACTERS))
            .default(DEFAULT_CATALOG_NAME_FORMAT)
            .validate {
                require(pattern.isNotBlank()) { Error.patternEmpty() }

                pattern.toCharArray().forEach {
                    require(ALLOWED_PATTERN_CHARACTERS.contains(it)) {
                        Error.patternHasIllegalCharacter(it)
                    }
                }

                try {
                    SimpleDateFormat(pattern).apply { timeZone = TimeZone.getDefault() }
                } catch (e: Exception) {
                    require(false) { Error.patternParsingError(e.message) }
                }
            }

    private val verbose: Boolean by option("-v", "--verbose", help = CliArgsDesc.verbose())
            .flag()

    private val maybeDirectory: File? by argument("dir", help = CliArgsDesc.workingDirectory())
            .file(exists = true, fileOkay = false, folderOkay = true, readable = true, writable = true )
            .optional()

    override fun run() {
        val dateFormatter = SimpleDateFormat(pattern, Locale.US)
                .apply { timeZone = TimeZone.getDefault() }
        val directory = maybeDirectory ?: File(".")

        thenDo.invoke(dateFormatter, directory, verbose)
    }
}
