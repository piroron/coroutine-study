@file:OptIn(KtorExperimentalLocationsAPI::class)

package com.example.controller

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.random.Random
import kotlin.system.measureTimeMillis

@Location("/api/non-blocking/{millisecond}")
class NonBlockingLocation(val millisecond: Long)

@Location("/api/blocking/{millisecond}")
class BlockingLocation(val millisecond: Long)

@Location("/api/with-blocking/{millisecond}")
class WithBlockingLocation(val millisecond: Long)

fun Route.waitController() {
    get<NonBlockingLocation> {

        val times = measureTimeMillis {
            delay(randomHeavy(it.millisecond))
        }

        call.respond(HttpStatusCode.OK, "Hello! time: $times")
    }

    get<BlockingLocation> {
        val processTime = measureTimeMillis {

            Thread.sleep(randomHeavy(it.millisecond))
        }

        call.respond(HttpStatusCode.OK, "Process Time: $processTime")
    }

    get<WithBlockingLocation> {
        val processTime = measureTimeMillis {

            withContext(Dispatchers.IO) {
                Thread.sleep(randomHeavy(it.millisecond))
            }
        }

        call.respond(HttpStatusCode.OK, "Process Time: $processTime")
    }
}

private fun randomHeavy(time: Long): Long {
    return time
}

private suspend fun wait(millisecond: Long) {
    withContext(Dispatchers.IO) {
        Thread.sleep(millisecond)
    }
}
