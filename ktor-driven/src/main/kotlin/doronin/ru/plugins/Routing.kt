package doronin.ru.plugins

import doronin.ru.services.FibonacciCalculator
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {

    routing {
        get("/fib/{element}") {
            val elementValue = FibonacciCalculator.calculateElement(call.parameters["element"]?.toLong() ?: 1L)
            call.respondText { elementValue.toString() }
        }
    }
}
