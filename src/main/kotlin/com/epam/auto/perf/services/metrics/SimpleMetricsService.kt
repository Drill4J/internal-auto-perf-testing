package com.epam.auto.perf.services.metrics

import com.sun.management.OperatingSystemMXBean
import mu.KotlinLogging
import java.lang.management.ManagementFactory
import java.lang.management.MemoryMXBean
import java.math.BigDecimal
import java.math.RoundingMode


class SimpleMetricsService(urlConnection: String) {

    private val logger = KotlinLogging.logger {}
    val metricsRetriever: MetricsRetriever = MetricsRetriever(urlConnection)
    fun getHeapUsage(): HeapModel {
        logger.info { "Retrieve heap usage." }
        val memoryMXBean = metricsRetriever.retrieveJMXMetric<MemoryMXBean>(ManagementFactory.MEMORY_MXBEAN_NAME)

        val memoryUsage = memoryMXBean.heapMemoryUsage
        val max = memoryUsage.max.toDouble()
        val used = memoryUsage.used
        val percentHeapUsage = BigDecimal((used / max) * 100).setScale(2, RoundingMode.HALF_EVEN).toDouble()

        return HeapModel(heapUsage = percentHeapUsage, rawHeap = used)
    }

    fun getCPUUsage(): Double {
        logger.info { "Retrieve cpu usage." }
        return metricsRetriever.retrieveJMXMetric<OperatingSystemMXBean>(ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME)
            .processCpuLoad
    }

}
