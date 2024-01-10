package com.epam.auto.perf

import com.epam.auto.perf.services.metrics.SimpleMetricsService
import kotlinx.coroutines.delay

suspend fun MainFacade.addJobTemplate(
    metricsService: SimpleMetricsService,
    prefix: String
) {
    try {
        val cpuUsage = metricsService.getCPUUsage().toString()
        logger.info { "$prefix. Got cpuUsage: $cpuUsage" }
        val heapModel = metricsService.getHeapUsage()
        logger.info { "$prefix. Got heapModel: $heapModel" }
        statisticsWriterService.writeToStatFile(
            prefix,
            cpuUsage,
            heapModel.heapUsage.toString(),
            heapModel.rawHeap.toString()
        )
        delay(3000)
    } catch (ex: Exception) {
        logger.error { "$prefix. Exception: $ex" }
        metricsService.metricsRetriever.resetConnection()
    }
}
