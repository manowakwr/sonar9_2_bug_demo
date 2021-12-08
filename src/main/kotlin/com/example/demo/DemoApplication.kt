package com.example.demo

import kotlinx.coroutines.runBlocking
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    val someClass = SomeClass()
    runBlocking {
        println(someClass.justReturnValue())
    }
    runApplication<DemoApplication>(*args)
}
