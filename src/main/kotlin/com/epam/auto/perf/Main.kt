package com.epam.auto.perf


import kotlin.time.Duration.Companion.seconds

fun main(args: Array<String>) {
    val numberOfRepeats = 1
    MainFacade().run {
        startAdminPart()
        startAppPart()
        confirmAppStarted()
        startAppTests(40.seconds, 0)
        deploySecondBuild()
        for (index in 2..<numberOfRepeats + 2) {
            startAppTests(40.seconds, index)
        }
        //TODO add clean-up: docker-compose down -v
    }
}
