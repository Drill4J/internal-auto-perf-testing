package com.epam.auto.perf

import com.epam.auto.perf.services.metrics.SimpleMetricsService
import kotlinx.coroutines.delay


suspend fun MainFacade.addJobTemplate(
    metricsService: SimpleMetricsService,
    prefix: String
) {
    val cpuUsage = metricsService.getCPUUsage().toString()
    logger.debug { "$prefix. Got cpuUsage: $cpuUsage" }
    val heapUsage = metricsService.getHeapUsage().toString()
    logger.debug { "$prefix. Got heapUsage: $heapUsage" }
    statisticsWriterService.writeToStatFile(prefix, cpuUsage, heapUsage)
    delay(1000)
}
