package edu.student.groom

import kotlinx.coroutines.*

fun main() =

    runBlocking {
        println("Main starts")
        joinAll(
            async { coroutine(1, 500) },
            async { coroutine(2, 3000) }
        )
        println("Main ends")
    }


suspend fun coroutine(number: Int, delay: Long) {

    println("coroutine $number starts work")
    delay(delay)
    println("coroutine $number has finished")

}