package com.practice.practicerestdocs.config

import mu.KLogger
import mu.KotlinLogging

fun logger(name: String): KLogger = KotlinLogging.logger(name)

inline fun <reified T : Any> T.logger(): KLogger = logger(T::class.java.name)
