package com.epam.auto.perf.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getCurrentTime(): String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm"))
