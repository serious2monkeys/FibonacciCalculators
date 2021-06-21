package ru.doronin.calculation

import io.vertx.core.AbstractVerticle
import io.vertx.core.Handler
import io.vertx.core.Promise
import io.vertx.core.eventbus.Message
import mu.KotlinLogging
import java.math.BigInteger


/* Created by doronantonin on 2021-06-21 */
/**
 * Компонент для выполнения непосредственных расчётов
 */
private val logger = KotlinLogging.logger {}

class FibCalculationVerticle : AbstractVerticle() {

    override fun start(startPromise: Promise<Void>) {
        vertx.eventBus().consumer(CALCULATE_ELEMENT_BUS, runCalculation())
            .completionHandler {
                if (it.succeeded()) {
                    logger.info { "Event Bus configured" }
                    startPromise.complete()
                } else {
                    logger.error { it.cause() }
                    startPromise.fail(it.cause())
                }
            }
    }

    private fun runCalculation(): Handler<Message<Long>> = Handler { longMessage ->
        val elementNumber = longMessage.body()
        longMessage.reply(calculate(elementNumber).toString())
    }

    private tailrec fun calculate(
        elementNumber: Long,
        prePreviousValue: BigInteger = BigInteger.ZERO,
        previousValue: BigInteger = BigInteger.ONE
    ): BigInteger =
        when (elementNumber) {
            0L -> prePreviousValue
            1L -> previousValue
            else -> calculate(elementNumber - 1, previousValue, prePreviousValue + previousValue)
        }

    companion object {
        const val CALCULATE_ELEMENT_BUS = "calculate.fibonacci.bus"
    }
}
