package com.epam.auto.perf


import kotlin.time.Duration.Companion.minutes

fun main(args: Array<String>) {
    val numberOfRepeats = 1
//    val runTestCommand = "./gradlew.bat clean :build1:test"
    val runTestCommand =
        "java -classpath C:\\projects\\Drill4j\\internal_spring_api_requester\\target\\classes;C:\\Users\\aseev\\.m2\\repository\\org\\jetbrains\\kotlin\\kotlin-stdlib-jdk8\\1.8.0\\kotlin-stdlib-jdk8-1.8.0.jar;C:\\Users\\aseev\\.m2\\repository\\org\\jetbrains\\kotlin\\kotlin-stdlib\\1.8.0\\kotlin-stdlib-1.8.0.jar;C:\\Users\\aseev\\.m2\\repository\\org\\jetbrains\\kotlin\\kotlin-stdlib-common\\1.8.0\\kotlin-stdlib-common-1.8.0.jar;C:\\Users\\aseev\\.m2\\repository\\org\\jetbrains\\annotations\\13.0\\annotations-13.0.jar;C:\\Users\\aseev\\.m2\\repository\\org\\jetbrains\\kotlin\\kotlin-stdlib-jdk7\\1.8.0\\kotlin-stdlib-jdk7-1.8.0.jar;C:\\Users\\aseev\\.m2\\repository\\com\\google\\code\\gson\\gson\\2.8.9\\gson-2.8.9.jar;C:\\Users\\aseev\\.m2\\repository\\org\\jetbrains\\kotlinx\\kotlinx-coroutines-core-jvm\\1.6.4\\kotlinx-coroutines-core-jvm-1.6.4.jar com.epam.drill4j.caller.DemoKt"
    MainFacade().run {
        startAdminPart()
        startAppPart()
        confirmAppStarted()
        startAppTests(10.minutes, 0, runTestCommand)
//        deploySecondBuild()
//        for (index in 2..<numberOfRepeats + 2) {
//            startAppTests(1.minutes, index)
//        }
        //TODO add clean-up: docker-compose down -v
    }
}
