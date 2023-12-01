package com.epam.auto.perf

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
    private val appMetricsService: SimpleMetricsService = SimpleMetricsService("service:jmx:rmi://0.0.0.0:9011/jndi/rmi://0.0.0.0:9010/jmxrmi"),
    private val adminMetricsService: SimpleMetricsService = SimpleMetricsService("service:jmx:rmi://0.0.0.0:9013/jndi/rmi://0.0.0.0:9012/jmxrmi"),
    private val executionService: ExecutionService =
        ExecutionService(workingDirPath = "C:\\projects\\Drill4j\\realworld-java-and-js-coverage"),
    val statisticsWriterService: StatisticsWriterService =
        StatisticsWriterService(fileName = "statistics/${getCurrentTime()}.txt", charset = Charset.defaultCharset())
) {
    init {
        statisticsWriterService.createStatFile()
    }

    val logger = KotlinLogging.logger {}

    private fun adminJob() = CoroutineScope(Dispatchers.Default).async {
        logger.info { "AdminJob was started." }
        while (isActive) {
            try {
                addJobTemplate(adminMetricsService, "admin")
            } catch (ex: Exception) {
                logger.error { ex }
            }
        }
    }

    private fun appJob() = CoroutineScope(Dispatchers.Default).async {
        logger.info { "AppJob was started." }
        while (isActive) {
            try {
                addJobTemplate(appMetricsService, "application")
            } catch (ex: Exception) {
                logger.error { ex }
                appMetricsService.metricsRetriever.resetConnection()
            }
        }
    }


    fun startAdminPart() {
        logger.info("Admin part was started.")
        executionService.runCommand("docker-compose -f docker/docker-compose-admin.yml up -d")
        adminJob()
    }

    fun startAppPart() {
        logger.info("Application part was started.")
        executionService.runCommand("docker-compose -f docker/docker-compose-app.yml up -d")
        appJob()
    }

    fun confirmAppStarted() {
        //Wait until app will be available. Approx time.
        Thread.sleep(5000)
    }

    fun startAppTests(delayTime: Duration, buildNumber: Int) {
        logger.info("Build: $buildNumber. Start application tests.")
        statisticsWriterService.writePlainText("Build: $buildNumber. Application tests started.")
        executionService.runCommand("./gradlew.bat clean :build1:test")
        logger.info { "Delay: $delayTime" }
        statisticsWriterService.writePlainText("Build: $buildNumber. Application tests finished.")
        Thread.sleep(delayTime.inWholeMilliseconds)
    }

    fun deploySecondBuild() {
        logger.info("Deploy build №2.")
        statisticsWriterService.writePlainText("Deploy build №2 started.")
        executionService.runCommand("docker compose -f docker/docker-compose-app.yml down")
        executionService.runCommand("docker compose -f docker/docker-compose-app2.yml up -d")
        statisticsWriterService.writePlainText("Deploy build №2 finished.")
        confirmAppStarted()
    }

}