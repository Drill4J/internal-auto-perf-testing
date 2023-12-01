package com.epam.auto.perf.services.stats

import mu.KotlinLogging
import java.io.File
import java.nio.charset.Charset
import java.time.LocalDateTime

class StatisticsWriterService(private val fileName: String, private val charset: Charset) {

    private val logger = KotlinLogging.logger {}

    fun createStatFile() {
        logger.info { "Create stats file with name: $fileName" }
        File(fileName).createNewFile()
    }

    fun writeToStatFile(prefix: String, cpuLoad: String, heapLoad: String) {
        val stringToWrite = "${LocalDateTime.now()} prefix: $prefix, CPU load: $cpuLoad%, Heap load: $heapLoad%"
        logger.debug { "Write stats to file: $fileName, content: $stringToWrite" }
        File(fileName).appendText("\n$stringToWrite", charset)
    }

    fun writePlainText(content: String) {
        logger.debug { "Write plain text: $content" }
        File(fileName).appendText("\n${LocalDateTime.now()} $content", charset)
    }
}

