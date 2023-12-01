package com.epam.auto.perf.services.metrics

import java.lang.Exception
import java.lang.management.ManagementFactory
import javax.management.MBeanServerConnection
import javax.management.ObjectName
import javax.management.remote.JMXConnectorFactory
import javax.management.remote.JMXServiceURL

class MetricsRetriever(val urlConnection: String) {

    private var connection: MBeanServerConnection? = null

    fun getConnection(): MBeanServerConnection? {
        if (connection == null) {
            connection = JMXConnectorFactory.connect(JMXServiceURL(urlConnection)).mBeanServerConnection
            return connection
        } else {
            return connection
        }
    }

    fun resetConnection() {
        connection = null
    }

    inline fun <reified T> retrieveJMXMetric(objectName: String): T {
        return ManagementFactory.newPlatformMXBeanProxy(
            getConnection(),
            ObjectName(objectName).toString(),
            T::class.java
        )
    }
}