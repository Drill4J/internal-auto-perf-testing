package com.epam.drill.tools

import com.epam.auto.perf.services.metrics.MetricsRetriever
import com.epam.auto.perf.utils.getCurrentTime
import com.sun.management.HotSpotDiagnosticMXBean

fun main() {
    val metricsRetriever = MetricsRetriever("service:jmx:rmi://0.0.0.0:9011/jndi/rmi://0.0.0.0:9010/jmxrmi")
    val hotSpotDiagnostic =
        metricsRetriever.retrieveJMXMetric<HotSpotDiagnosticMXBean>("com.sun.management:type=HotSpotDiagnostic")
    hotSpotDiagnostic.dumpHeap("${getCurrentTime()}.hprof", false)
}