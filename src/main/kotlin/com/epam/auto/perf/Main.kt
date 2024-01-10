package com.epam.auto.perf


import com.epam.auto.perf.config.Config
import kotlin.time.Duration.Companion.minutes

fun main(args: Array<String>) {
    val runTestCommand = Config.getProperty("command-to-run-tests")
    MainFacade().run {
        //NOTE: use 'runJobs' method if you want to start and stop admin and application manually
        //runJobs()
        startAdminPart()
        startAppPart()
        confirmAppStarted()
        startAppTests(10.minutes, 0, runTestCommand)
        deploySecondBuild()
        startAppTests(10.minutes, 0, runTestCommand)
        //TODO add clean-up: docker-compose down -v
    }
}
