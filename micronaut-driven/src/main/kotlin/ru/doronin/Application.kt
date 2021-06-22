package ru.doronin

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
    build()
            .args(*args)
            .packages("ru.doronin")
            .start()
}

