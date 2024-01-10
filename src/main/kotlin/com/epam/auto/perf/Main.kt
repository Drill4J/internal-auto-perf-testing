package com.epam.auto.perf


import kotlin.time.Duration.Companion.minutes

//TODO add config file
fun main(args: Array<String>) {
    val runTestCommand = "./gradlew.bat clean :build1:test"
    MainFacade().run {
//        runJobs()
        startAdminPart()
        startAppPart()
        confirmAppStarted()
        startAppTests(10.minutes, 0, runTestCommand)
        deploySecondBuild()
        startAppTests(10.minutes, 0, runTestCommand)
        //TODO add clean-up: docker-compose down -v
    }
}
