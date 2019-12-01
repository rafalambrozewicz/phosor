package app.demo.phosor.core

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.Directory
import com.drew.metadata.Metadata
import com.drew.metadata.exif.ExifIFD0Directory
import com.drew.metadata.exif.ExifSubIFDDirectory
import com.drew.metadata.mov.QuickTimeDirectory
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class Phosor(val verbose: Boolean) {

    fun process(workingDirectory: File, dateFormatter: SimpleDateFormat) {
        log("Phosor started at ${ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"))}, " +
                "workingDirectory: ${workingDirectory.absolutePath}, " +
                "pattern: ${dateFormatter.toPattern()}")

        (Files.list(workingDirectory.toPath())).use { files ->
            files.forEach {
                if (it != null && it.toFile().isFile) {

                    val maybeCreationDate = it.toFile().creationDateOrNull()

                    if (maybeCreationDate == null) { log("Creation date for ${it.toFile().name} cannot be determined") }

                    maybeCreationDate?.let { creationDate ->
                        val directoryNameForFile = dateFormatter.format(creationDate)
                        val directoryPathForFile = workingDirectory.absolutePath + "/" + directoryNameForFile
                        File(directoryPathForFile).makeDirectoryIfNeeded()

                        val fromPath = it
                        val toPath = Paths.get(directoryPathForFile + "/" + it.toFile().name)
                        Files.move(fromPath, toPath)
                        log("File ${it.toFile().name} created at " +
                                "${ZonedDateTime.ofInstant(creationDate.toInstant(), ZoneId.of("UTC"))} " +
                                "got moved to ${directoryNameForFile}")
                    }
                }
            }

        }
    }

    private fun log(msg: String) { if (verbose) { println(msg) } }

    private fun File.creationDateOrNull(): Date? {
        val maybeMetadata = try { ImageMetadataReader.readMetadata(this) } catch (e: Exception) { null }
        val maybeCreationDate = arrayListOf(
                maybeMetadata?.datetimeOriginal(),
                maybeMetadata?.datetimeDigitized(),
                maybeMetadata?.datetime(),
                maybeMetadata?.movCreationDate()).firstOrNull { it != null }

        return  maybeCreationDate
    }

    private fun Metadata.datetimeOriginal(): Date? = this.dateOrNull(ExifSubIFDDirectory::class.java, ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL)

    fun <T : Directory?> Metadata.dateOrNull(directory: Class<T>, tagType: Int): Date? {
        try {
            return this.getFirstDirectoryOfType(directory)?.getDate(tagType)
        } catch (e: Exception) {
            return null
        }
    }

    private fun Metadata.datetimeDigitized(): Date? = this.dateOrNull(ExifSubIFDDirectory::class.java, ExifSubIFDDirectory.TAG_DATETIME_DIGITIZED)

    private fun Metadata.datetime(): Date? = this.dateOrNull(ExifIFD0Directory::class.java, ExifIFD0Directory.TAG_DATETIME)

    private fun Metadata.movCreationDate(): Date? = this.dateOrNull(QuickTimeDirectory::class.java, QuickTimeDirectory.TAG_CREATION_TIME)

    private fun File.makeDirectoryIfNeeded() {
        if(!this.isDirectory) {
            this.mkdirs()
        }
    }
}