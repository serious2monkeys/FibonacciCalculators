package ru.doronin

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import mu.KotlinLogging
import ru.doronin.calculation.FibCalculationVerticle
import ru.doronin.http.HttpServerVerticle

private val logger = KotlinLogging.logger {}

class MainVerticle : AbstractVerticle() {

    override fun start(startPromise: Promise<Void>) {
        try {
            vertx.deployVerticle(FibCalculationVerticle())
                .compose { vertx.deployVerticle(HttpServerVerticle()) }
                .onSuccess {
                    logger.error { "Deploy success: $it" }
                    startPromise.complete()
                }
                .onFailure {
                    logger.error { "Failure: $it?.cause" }
                    startPromise.fail(it.cause)
                }
        } catch (exception: Throwable) {
            logger.error { "$exception" }
        }
    }
}
