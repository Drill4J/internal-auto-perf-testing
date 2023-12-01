package com.epam.auto.perf.services.excecution

import com.epam.auto.perf.utils.getCurrentTime
import mu.KotlinLogging
import java.io.File

class ExecutionService(private val workingDirPath: String) {
    private val logger = KotlinLogging.logger {}
    val file = File("logs/${getCurrentTime()}.txt").apply { this.createNewFile() }

    fun runCommand(command: String) {
        logger.debug { "Run command: $command  at dir: $workingDirPath" }
        ProcessBuilder("\\s".toRegex().split(command))
            .directory(File(workingDirPath))
            .redirectOutput(ProcessBuilder.Redirect.to(file))
            .redirectError(ProcessBuilder.Redirect.to(file))
            .start()
            .waitFor()
    }
}