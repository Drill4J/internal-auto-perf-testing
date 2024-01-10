package com.epam.auto.perf

import com.epam.auto.perf.config.Config
import com.epam.auto.perf.services.excecution.ExecutionService
import com.epam.auto.perf.services.metrics.SimpleMetricsService
import com.epam.auto.perf.services.stats.StatisticsWriterService
import com.epam.auto.perf.utils.getCurrentTime
import kotlinx.coroutines.*
import java.nio.charset.Charset
import kotlin.time.Duration
import mu.KotlinLogging
import java.lang.Exception

class MainFacade(
    private val appMetricsService: SimpleMetricsService = SimpleMetricsService(Config.getProperty("app-jmx-connection-url")),
    private val adminMetricsService: SimpleMetricsService = SimpleMetricsService(Config.getProperty("admin-jmx-connection-url")),
    private val dockerExecService: ExecutionService = ExecutionService(Config.getProperty("execution-command-path")),
    val statisticsWriterService: StatisticsWriterService =
        StatisticsWriterService(
            fileName = "elastic/statistics/${getCurrentTime()}.txt",
            charset = Charset.defaultCharset()
        )
) {
    init {
        statisticsWriterService.createStatFile()
    }

    val logger = KotlinLogging.logger {}

    private fun adminJob() = CoroutineScope(Dispatchers.Default).async {
        logger.info { "AdminJob was started." }
        while (isActive) {
            addJobTemplate(adminMetricsService, "admin")
        }
    }

    private fun appJob() = CoroutineScope(Dispatchers.Default).async {
        logger.info { "AppJob was started." }
        while (isActive) {
            addJobTemplate(appMetricsService, "application")
        }
    }

    fun startAdminPart() {
        logger.info("Admin part was started.")
        dockerExecService.runCommand("docker-compose -f docker/docker-compose-admin.yml up -d")
        adminJob()
    }

    fun startAppPart() {
        logger.info("Application part was started.")
        dockerExecService.runCommand("docker-compose -f docker/docker-compose-app.yml up -d")
        appJob()
    }

    fun confirmAppStarted() {
        //Wait until app will be available. Approx time.
        Thread.sleep(5000)
    }

    fun runJobs() {
        appJob()
        adminJob()
    }

    fun startAppTests(delayTime: Duration, buildNumber: Int, command: String) {
        logger.info { "Delay before tests: $delayTime" }
        Thread.sleep(delayTime.inWholeMilliseconds)
        logger.info("Build: $buildNumber. Start application tests.")
        statisticsWriterService.writePlainText("Build: $buildNumber. Application tests started.")
        dockerExecService.runCommand(command)
        logger.info { "Delay after tests: $delayTime" }
        statisticsWriterService.writePlainText("Build: $buildNumber. Application tests finished.")
        Thread.sleep(delayTime.inWholeMilliseconds)
    }

    fun deploySecondBuild() {
        logger.info("Deploy build №2.")
        statisticsWriterService.writePlainText("Deploy build №2 started.")
        dockerExecService.runCommand("docker compose -f docker/docker-compose-app.yml down")
        dockerExecService.runCommand("docker compose -f docker/docker-compose-app2.yml up -d")
        statisticsWriterService.writePlainText("Deploy build №2 finished.")
        confirmAppStarted()
    }

}